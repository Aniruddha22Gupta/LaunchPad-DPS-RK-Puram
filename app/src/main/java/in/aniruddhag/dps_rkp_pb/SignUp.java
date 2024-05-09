package in.aniruddhag.dps_rkp_pb;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.util.Patterns;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.auth.api.identity.BeginSignInRequest;
import com.google.android.gms.auth.api.identity.SignInCredential;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.GoogleAuthProvider;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class SignUp extends AppCompatActivity {

    private FirebaseAuth mAuth;
    private FirebaseDatabase mDatabase;
    private DatabaseReference databaseReference;
    private static FirebaseUser currentUser;
    private String Username, Password;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);

        EditText txtin_UsID = findViewById(R.id.txtin_UsID);
        EditText txtin_UsPass = findViewById(R.id.txtin_UsPass);
        Button btn_SU = findViewById(R.id.btn_SU);

        mAuth = FirebaseAuth.getInstance();
        SetFirebaseCurrentUser(mAuth.getCurrentUser());

        txtin_UsID.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
                String sEditable;
                sEditable = editable.toString();
                if (!sEditable.isEmpty()) {
                    if (!Patterns.EMAIL_ADDRESS.matcher(sEditable).matches()) {
                        txtin_UsID.setError("Wrong Email Format");
                    }
                }
            }
        });

        txtin_UsPass.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
                String sEditable;
                sEditable = editable.toString();
                if (!sEditable.isEmpty()) {
                    if (sEditable.length() < 8) {
                        txtin_UsPass.setError("Password should be at least of 8 characters.");
                    }
                }
            }
        });

        btn_SU.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Username = txtin_UsID.getText().toString();
                Password = txtin_UsPass.getText().toString();

                if (Patterns.EMAIL_ADDRESS.matcher(Username).matches()) {
                    mAuth.createUserWithEmailAndPassword(Username, Password).addOnCompleteListener(task -> {
                        if (task.isSuccessful()) {
                            SetFirebaseCurrentUser(mAuth.getCurrentUser());

                            Intent i = new Intent(SignUp.this, LandingPage.class);
                            startActivity(i);
                        } else
                            Toast.makeText(SignUp.this, "Couldn't Successfully Create an Account. Error: " + task.getException(), Toast.LENGTH_LONG).show();

                    });
                } else {
                    Toast.makeText(SignUp.this, "failed", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    public void clickLI(View view) {
        Intent i = new Intent(this, LogIn.class);
        startActivity(i);
    }

    public static void SetFirebaseCurrentUser(FirebaseUser firebaseUser) {
        currentUser = firebaseUser;
    }

    public FirebaseUser GetFirebaseCurrentUser() {
        return currentUser;
    }
}