package com.example.winecompendium;

import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.firestore.FirebaseFirestore;

public class register extends AppCompatActivity
{
    //Declaring any UI element for interaction
    private Button btn_register;
    private TextView btn_alreadyMember;


    //Declaring any UI element for interaction

    EditText Email;
    EditText name;
    EditText surname;
    EditText password;
    EditText confirmPassword;

    private static final String TAG = "EmailPassword";

    FirebaseAuth mAuth;
    private FirebaseFirestore fstore;
    String userID;
    FirebaseUser fuser;

    users _user;

    private FirebaseDatabase database = FirebaseDatabase.getInstance();
    private DatabaseReference UsersRef = database.getReference("Users");

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        //All the findViewById methods are created
        Email = findViewById(R.id.email_input);
        name = findViewById(R.id.name_input);
        surname = findViewById(R.id.surname_input);
        password = findViewById(R.id.password_input);
        confirmPassword = findViewById(R.id.confirmPassword_input);
        btn_register = findViewById(R.id.btn_register);
        btn_alreadyMember = findViewById(R.id.btn_alreadyMember);

        //Code for firebase database
        mAuth = FirebaseAuth.getInstance();
        fstore = FirebaseFirestore.getInstance();
        fuser = mAuth.getCurrentUser();

        _user = new users();


        /*
         *  SUMMERY: When the user clicks on the Register button, Proceed to WriteDate to Firebase
         */
        btn_register.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {
                if (password.getText().toString().equals(confirmPassword.getText().toString()))
                {
                    //If passwords match
                    //method to check if email is valid. If valid then proceed to register user
                    if (emailValidator(Email.getText().toString())) {
                        registerUserWithFirebase();
                    } else
                    {
                        Toast.makeText(register.this,"Invalid email! Please try again.",Toast.LENGTH_LONG);
                    }
                }
                else //if passwords don't match
                {
                    Toast.makeText(register.this,  "Your passwords dont match! Try Again!", Toast.LENGTH_LONG);
                }
            }
        });

        /*
         *  SUMMERY: When the user clicks on the Already a Member button, Return to Login
         */
        btn_alreadyMember.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {
                Intent i = new Intent(register.this, login.class);
                startActivity(i);
            }
        });
    }

    /** SUMMARY: Saves the input data to Firebase. The user is then sent a verification email that
     *           needs to be completed before they can login
     */
    private void registerUserWithFirebase()
    {
        String _name = name.getText().toString();
        String _surname = surname.getText().toString();
        String _Email = Email.getText().toString();
        String _password = password.getText().toString();

        mAuth.createUserWithEmailAndPassword(_Email, _password)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            // Sign in success, update UI with the signed-in user's information
                            Log.d(TAG, "createUserWithEmail:success");

                            sendVerificationEmail();
                            addUsertoDatabase(_name, _surname, _Email, mAuth.getCurrentUser().getUid().toString());

                        } else {
                            // If sign in fails, display a message to the user.
                            Log.w(TAG, "createUserWithEmail:failure", task.getException());
                            Toast.makeText(register.this, "Authentication failed.",
                                    Toast.LENGTH_SHORT).show();
                        }
                    }
                });

    }

    //------------------------------ Method to validate register email -----------------------------

    public boolean emailValidator(String email) {

        if (!email.isEmpty() && Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            return true;
        } else {
            return false;
        }
    }

    //----------------------------------------------------------------------------------------------

    private void showThankYouDialog()
    {
        Dialog dialog = new Dialog(this, R.style.DialogStyle);
        dialog.setCanceledOnTouchOutside(false); //-- To prevent a user from clicking away --
        dialog.setContentView(R.layout.dialog_account_created);
        dialog.getWindow().setBackgroundDrawableResource(R.drawable.custom_container_login); //Adds greyness to the corners too

        Button btn_thanks = dialog.findViewById(R.id.btn_thanks);
        btn_thanks.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {
                startActivity(new Intent(getApplicationContext(), login.class)); // New way of doing intent :-)
                dialog.dismiss();
            }
        });
        dialog.show();
    }


    //------------------------------ Method to send verification link ------------------------------

    private void sendVerificationEmail() {
        FirebaseUser user = mAuth.getCurrentUser();

        user.sendEmailVerification().addOnCompleteListener(this, new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                if (task.isSuccessful()) {
                    showThankYouDialog();
                    Log.d("onSuccess_TAG", "onSuccess: Email verification link sent" + userID);
                }
                else
                {
                    Log.e(TAG, "sendEmailVerification", task.getException());
                    Toast.makeText(register.this,
                            "Failed to send verification email.",
                            Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    //----------------------------------------------------------------------------------------------

    //-------------------------- Method to add the user data to firebase database  -----------------

    private void  addUsertoDatabase(String _fname, String _lname, String _email, String _id) {

        userID = mAuth.getCurrentUser().getUid();

        String userID = mAuth.getCurrentUser().getUid().toString();
        DatabaseReference ref = UsersRef.child(userID).child("Account");

        _user.setFirstName(_fname);
        _user.setLastName(_lname);
        _user.setEmail(_email);
        _user.setUserID(userID);
        ref.push().setValue(_user);

        AddCategoriesData();
        AddCategoriesAddGoal();

    }

    //----------------------------------------------------------------------------------------------

    // ---------------- Write Categories accordingly to user's ID to database ----------------------
    private void AddCategoriesData() {
        Categories_Data data = new Categories_Data();
        String userID = mAuth.getCurrentUser().getUid().toString();

        /*
        Update personal user's database with all the wine categories
         */
        DatabaseReference ref = UsersRef.child(userID);
        DatabaseReference catRef = ref.child("Categories");
        DatabaseReference subTypeRed = catRef.child("SubType");

        catRef.child("WineType").updateChildren(data.WineTypesMap());

        subTypeRed.child("Red").updateChildren(data.SubTypeRedMap());
        subTypeRed.child("White").updateChildren(data.SubTypeWhite());
        subTypeRed.child("Rosè").updateChildren(data.SubTypeRoseMap());
        subTypeRed.child("Sparkling").updateChildren(data.SubTypeSparklingMap());
        subTypeRed.child("Dessert").updateChildren(data.SubTypeDessertMap());
        subTypeRed.child("Fortified").updateChildren(data.SubTypeFortifiedMap());

        catRef.child("Origin").updateChildren(data.OriginMap());
        catRef.child("BottleType").updateChildren(data.BottleTypeMap());

    }
    //----------------------------------------------------------------------------------------------

    // ---------------- Write Categories for Add Goal accordingly to user's ID to database ----------------------
    private void AddCategoriesAddGoal() {

        Categories_Data_Add_Goal data = new Categories_Data_Add_Goal();
        String userID = mAuth.getCurrentUser().getUid().toString();

        DatabaseReference ref = UsersRef.child(userID);
        DatabaseReference catRef = ref.child("AddGoal_Categories").child("AllCategories");

        catRef.updateChildren(data.Categories());


    }
    //----------------------------------------------------------------------------------------------
}
/* -------------------------------------===< END OF FILE >===-------------------------------------*/
