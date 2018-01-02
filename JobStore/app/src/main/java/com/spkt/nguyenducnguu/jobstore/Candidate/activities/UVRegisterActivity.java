package com.spkt.nguyenducnguu.jobstore.Candidate.activities;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.spkt.nguyenducnguu.jobstore.Const.Node;
import com.spkt.nguyenducnguu.jobstore.Const.RequestCode;
import com.spkt.nguyenducnguu.jobstore.Database.Database;
import com.spkt.nguyenducnguu.jobstore.FontManager.FontManager;
import com.spkt.nguyenducnguu.jobstore.Interface.OnGetDataListener;
import com.spkt.nguyenducnguu.jobstore.Models.Address;
import com.spkt.nguyenducnguu.jobstore.Models.Candidate;
import com.spkt.nguyenducnguu.jobstore.Models.CandidateDetail;
import com.spkt.nguyenducnguu.jobstore.Models.Diploma;
import com.spkt.nguyenducnguu.jobstore.Models.Roles;
import com.spkt.nguyenducnguu.jobstore.Models.WorkExp;
import com.spkt.nguyenducnguu.jobstore.R;
import com.spkt.nguyenducnguu.jobstore.ResultRegisterActivity;
import com.spkt.nguyenducnguu.jobstore.Select.activities.SelectCarrerActivity;
import com.spkt.nguyenducnguu.jobstore.Select.activities.SelectExperienceActivity;
import com.spkt.nguyenducnguu.jobstore.Select.activities.SelectLevelActivity;
import com.spkt.nguyenducnguu.jobstore.Select.activities.SelectSalaryActivity;
import com.spkt.nguyenducnguu.jobstore.Select.activities.SelectWorkPlaceActivity;
import com.spkt.nguyenducnguu.jobstore.Select.activities.SelectWorkTypeActivity;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

import mabbas007.tagsedittext.TagsEditText;

public class UVRegisterActivity extends AppCompatActivity {
    private FirebaseAuth mAuth = FirebaseAuth.getInstance();
    public List<WorkExp> lstWorkExp = new ArrayList<WorkExp>();
    public List<Diploma> lstDiploma = new ArrayList<Diploma>();
    private Uri CVUri = null;

    Calendar cal = Calendar.getInstance();
    ImageView imgv_Back, imgv_RemoveCV;
    Button btn_Register;
    EditText txt_Email, txt_Password, txt_ConfirmPassword, txt_FullName,
            txt_BirthDay, txt_Gender, txt_Description, txt_Tag, txt_CV, txt_Phone, txt_FacebookURL, txt_Address;
    TagsEditText txt_WorkType, txt_Career, txt_Level, txt_Experience, txt_Salary, txt_WorkPlace;
    Button btn_AddWorkType, btn_AddCareer, btn_AddLevel, btn_AddExperience, btn_AddSalary, btn_AddWorkPlace;
    Button btn_AddWorkExps, btn_AddDiplomas;
    ViewGroup vg_WorkExp, vg_Diploma;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_uv_register);

        addView();
        addEvent();
        setIcon();
    }

    private boolean ValidateInputData() {
        String email = txt_Email.getText().toString().trim();
        String emailPattern = "[a-zA-Z0-9._-]+@[a-z]+\\.+[a-z]+";
        if (!email.matches(emailPattern)) {
            Toast.makeText(this, "Địa chỉ email không hợp lệ!", Toast.LENGTH_SHORT).show();
            txt_Email.requestFocus();
            return false;
        }

        if (txt_Password.getText().length() < 6) {
            Toast.makeText(this, "Mật khẩu phải có ít nhất 6 ký tự!", Toast.LENGTH_SHORT).show();
            txt_Password.requestFocus();
            return false;
        }

        if (!txt_Password.getText().toString().equals(txt_ConfirmPassword.getText().toString())) {
            Toast.makeText(this, "Mật khẩu nhập lại không khớp!", Toast.LENGTH_SHORT).show();
            txt_ConfirmPassword.setText("");
            txt_ConfirmPassword.requestFocus();
            return false;
        }

        if (txt_FullName.getText().toString().trim().length() == 0) {
            Toast.makeText(this, "Vui lòng nhập họ tên của bạn!", Toast.LENGTH_SHORT).show();
            txt_FullName.requestFocus();
            return false;
        }
        if (txt_BirthDay.getText().toString().trim().length() == 0) {
            Toast.makeText(this, "Vui lòng chọn ngày sinh của bạn!", Toast.LENGTH_SHORT).show();
            return false;
        }
        if (txt_Gender.getText().toString().trim().length() == 0) {
            Toast.makeText(this, "Vui lòng chọn giới tính của bạn!", Toast.LENGTH_SHORT).show();
            return false;
        }
        if (txt_Description.getText().toString().trim().length() == 0) {
            Toast.makeText(this, "Vui lòng giới thiệu một chút về bạn!", Toast.LENGTH_SHORT).show();
            txt_Description.requestFocus();
            return false;
        }
        if (txt_Tag.getText().toString().trim().length() == 0) {
            Toast.makeText(this, "Vui lòng nhập một thẻ tag để nhà tuyển dụng có thể dễ dàng tìm thấy bạn!", Toast.LENGTH_SHORT).show();
            txt_Tag.requestFocus();
            return false;
        }
        if (txt_WorkPlace.getTags().size() == 0) {
            Toast.makeText(this, "Vui lòng chọn địa điểm làm việc!", Toast.LENGTH_SHORT).show();
            txt_WorkPlace.requestFocus();
            return false;
        }
        if (txt_WorkType.getTags().size() == 0) {
            Toast.makeText(this, "Vui lòng chọn loại hình công việc!", Toast.LENGTH_SHORT).show();
            txt_WorkType.requestFocus();
            return false;
        }
        if (txt_Career.getTags().size() == 0) {
            Toast.makeText(this, "Vui lòng chọn ngành nghề!", Toast.LENGTH_SHORT).show();
            txt_Career.requestFocus();
            return false;
        }
        if (txt_Level.getTags().size() == 0) {
            Toast.makeText(this, "Vui lòng chọn trình độ!", Toast.LENGTH_SHORT).show();
            txt_Level.requestFocus();
            return false;
        }
        if (txt_Experience.getTags().size() == 0) {
            Toast.makeText(this, "Vui lòng chọn kinh nghiệm!", Toast.LENGTH_SHORT).show();
            txt_Experience.requestFocus();
            return false;
        }
        if (txt_Salary.getTags().size() == 0) {
            Toast.makeText(this, "Vui lòng chọn mức lương!", Toast.LENGTH_SHORT).show();
            txt_Salary.requestFocus();
            return false;
        }
        if (txt_CV.getText().toString().trim().length() == 0) {
            Toast.makeText(this, "Vui lòng chọn CV của bạn!", Toast.LENGTH_SHORT).show();
            txt_CV.requestFocus();
            return false;
        }
        if (txt_Phone.getText().toString().trim().length() == 0) {
            Toast.makeText(this, "Vui lòng nhập số điện thoại của bạn!", Toast.LENGTH_SHORT).show();
            txt_Phone.requestFocus();
            return false;
        }
        if (txt_Address.getText().toString().trim().length() == 0) {
            Toast.makeText(this, "Vui lòng nhập địa chỉ hiện tại của bạn!", Toast.LENGTH_SHORT).show();
            txt_Address.requestFocus();
            return false;
        }
        return true;
    }

    private void Register() {
        if (!ValidateInputData()) return;

        final ProgressDialog dialog = ProgressDialog.show(this, "",
                "Please wait...", true);

        mAuth.createUserWithEmailAndPassword(txt_Email.getText().toString().trim(), txt_Password.getText().toString())
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            Candidate c = new Candidate();
                            CandidateDetail cd = new CandidateDetail();

                            c.setEmail(txt_Email.getText().toString());
                            c.setFullName(txt_FullName.getText().toString());
                            c.setBirthday(cal.getTime().getTime());
                            c.setGender(txt_Gender.getText().toString() == "Nam" ? 0 : 1);
                            c.setDescription(txt_Description.getText().toString());
                            c.setPhone(txt_Phone.getText().toString());
                            c.setFacebookURL(txt_FacebookURL.getText().toString());
                            c.setAddress(Address.getAddressFromLocationName(txt_Address.getText().toString(), UVRegisterActivity.this));
                            c.setCreateAt((new Date()).getTime());
                            c.setStatus(1);

                            cd.setTag(txt_Tag.getText().toString());
                            cd.setWorkPlaces(txt_WorkPlace.getTags().toString().substring(1, txt_WorkPlace.getTags().toString().length() - 1));
                            cd.setWorkTypes(txt_WorkType.getTags().toString().substring(1, txt_WorkType.getTags().toString().length() - 1));
                            cd.setCareers(txt_Career.getTags().toString().substring(1, txt_Career.getTags().toString().length() - 1));
                            cd.setLevel(txt_Level.getTags().toString().substring(1, txt_Level.getTags().toString().length() - 1));
                            cd.setExperience(txt_Experience.getTags().toString().substring(1, txt_Experience.getTags().toString().length() - 1));
                            cd.setSalary(txt_Salary.getTags().toString().substring(1, txt_Salary.getTags().toString().length() - 1));
                            HashMap<String, WorkExp> we = new HashMap<String, WorkExp>();
                            for (WorkExp w : lstWorkExp)
                                we.put(w.getId().toString(), w);
                            HashMap<String, Diploma> di = new HashMap<String, Diploma>();
                            for (Diploma d : lstDiploma)
                                di.put(d.getId().toString(), d);
                            cd.setWorkExps(we);
                            cd.setDiplomas(di);

                            c.setCandidateDetail(cd);
                            //Thêm dữ liệu
                            Database.addDataWithKey(Node.CANDIDATES, mAuth.getCurrentUser().getUid(), c);
                            //Phân quyền
                            Roles role = new Roles(c.getEmail(), 1);
                            Database.addDataWithKey(Node.ROLES, mAuth.getCurrentUser().getUid(), role);

                            if (CVUri != null) {
                                uploadCV(mAuth.getCurrentUser().getUid());
                            }
                            dialog.dismiss();
                            //Thông báo
                            showSuccessNotification();
                        } else {
                            dialog.dismiss();
                            //Thông báo
                            showFailedNotification();
                        }
                    }
                });
    }

    private void showSuccessNotification() {
        Intent intent = new Intent(this, ResultRegisterActivity.class);
        Bundle bundle = new Bundle();
        bundle.putString("title", "Congratulations");
        bundle.putString("content", "Tài khoản của bạn đã được tạo thành công, bạn có thể đăng nhập ngay bây giờ." +
                " Cảm ơn bạn đã sử dụng Jobstore!");
        bundle.putString("team", "SN UTE Team");
        bundle.putBoolean("success", true);
        intent.putExtra("bundle", bundle);
        startActivity(intent);
    }

    private void showFailedNotification() {
        Intent intent = new Intent(this, ResultRegisterActivity.class);
        Bundle bundle = new Bundle();
        bundle.putString("title", "Failed");
        bundle.putString("content", "Đã có lỗi trong quá trình đăng ký, có thể do tài khoản này đã được đăng ký " +
                "trước đó. Bạn vui lòng kiểm tra lại, nếu quên mật khẩu bạn có thể lấy lại mật khẩu ở màn hình Login " +
                "và chọn Quên mật khẩu. Cảm ơn bạn đã sử dụng Jobstore!");
        bundle.putString("team", "SN UTE Team");
        bundle.putBoolean("success", false);
        intent.putExtra("bundle", bundle);
        startActivity(intent);
    }

    private void uploadCV(final String UserId) {
        String[] arr = txt_CV.getText().toString().split("\\.");
        if(arr.length == 0) return;

        StorageReference ref = FirebaseStorage.getInstance().getReference()
                .child(FirebaseAuth.getInstance().getCurrentUser().getUid() + "/CV." + arr[arr.length - 1]);

        UploadTask uploadTask = ref.putFile(CVUri);

        uploadTask.addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Toast.makeText(UVRegisterActivity.this, "Không thể lưu CV!", Toast.LENGTH_LONG).show();
            }
        }).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
            @Override
            public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                final Uri downloadUrl = taskSnapshot.getDownloadUrl();
                Database.getData(Node.CANDIDATES + "/" + UserId, new OnGetDataListener() {
                    @Override
                    public void onSuccess(DataSnapshot dataSnapshot) {
                        Candidate c = dataSnapshot.getValue(Candidate.class);
                        c.getCandidateDetail().setCV(downloadUrl.toString());
                        Database.updateData(Node.CANDIDATES, UserId, c);
                    }

                    @Override
                    public void onFailed(DatabaseError databaseError) {

                    }
                });
            }
        });
    }

    private void loadDiploma() {
        vg_Diploma.removeAllViews();
        for (int i = 0; i < lstDiploma.size(); i++) {
            LayoutInflater vi = (LayoutInflater) getApplicationContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            View view = vi.inflate(R.layout.list_item_diploma_layout, null);

            LinearLayout ln_Parent = (LinearLayout) view.findViewById(R.id.ln_Parent);
            LinearLayout ln_Remove = (LinearLayout) view.findViewById(R.id.ln_Remove);
            Button btn_Scores = (Button) view.findViewById(R.id.btn_Scores);
            TextView txt_Id = (TextView) view.findViewById(R.id.txt_Id);
            TextView txt_Name = (TextView) view.findViewById(R.id.txt_Name);
            TextView txt_IssuedBy = (TextView) view.findViewById(R.id.txt_IssuedBy);
            TextView txt_IssuedDate = (TextView) view.findViewById(R.id.txt_IssuedDate);
            TextView txt_Ranking = (TextView) view.findViewById(R.id.txt_Ranking);

            txt_Id.setText(lstDiploma.get(i).getId() + "");
            btn_Scores.setText(lstDiploma.get(i).getScores() + "");
            txt_Name.setText(lstDiploma.get(i).getName());
            txt_IssuedBy.setText(lstDiploma.get(i).getIssuedBy());
            SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
            txt_IssuedDate.setText(sdf.format((new Date(lstDiploma.get(i).getIssuedDate())).getTime()));
            txt_Ranking.setText(lstDiploma.get(i).getRanking());

            ln_Parent.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    LinearLayout parent = (LinearLayout) v.getParent();
                    Button btn_Scores = (Button) parent.findViewById(R.id.btn_Scores);
                    TextView txt_Id = (TextView) parent.findViewById(R.id.txt_Id);
                    TextView txt_Name = (TextView) parent.findViewById(R.id.txt_Name);
                    TextView txt_IssuedBy = (TextView) parent.findViewById(R.id.txt_IssuedBy);
                    TextView txt_IssuedDate = (TextView) parent.findViewById(R.id.txt_IssuedDate);
                    TextView txt_Ranking = (TextView) parent.findViewById(R.id.txt_Ranking);

                    Intent intent = new Intent(UVRegisterActivity.this, UVDiplomaActivity.class);
                    intent.putExtra(UVDiplomaActivity.ID, txt_Id.getText().toString());
                    intent.putExtra(UVDiplomaActivity.SCORES, btn_Scores.getText().toString());
                    intent.putExtra(UVDiplomaActivity.NAME, txt_Name.getText().toString());
                    intent.putExtra(UVDiplomaActivity.ISSUEDBY, txt_IssuedBy.getText().toString());
                    intent.putExtra(UVDiplomaActivity.ISSUEDDATE, txt_IssuedDate.getText().toString());
                    intent.putExtra(UVDiplomaActivity.RANKING, txt_Ranking.getText().toString());

                    startActivityForResult(intent, RequestCode.ADD_DIPLOMA);
                }
            });

            ln_Remove.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    LinearLayout parent = (LinearLayout) v.getParent();
                    TextView txt_Id = (TextView) parent.findViewById(R.id.txt_Id);
                    try {
                        Long Id = Long.parseLong(txt_Id.getText().toString());
                        for (int i = 0; i < lstDiploma.size(); i++)
                            if (lstDiploma.get(i).getId().equals(Id)) {
                                lstDiploma.remove(i);
                                break;
                            }
                        loadDiploma();
                    } catch (Exception ex) {

                    }
                }
            });
            vg_Diploma.addView(view, 0, new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,
                    ViewGroup.LayoutParams.MATCH_PARENT));
        }
    }

    private void loadWorkExp() {
        vg_WorkExp.removeAllViews();
        for (int i = 0; i < lstWorkExp.size(); i++) {
            LayoutInflater vi = (LayoutInflater) getApplicationContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            View view = vi.inflate(R.layout.list_item_workexp_layout, null);

            LinearLayout ln_Parent = (LinearLayout) view.findViewById(R.id.ln_Parent);
            LinearLayout ln_Remove = (LinearLayout) view.findViewById(R.id.ln_Remove);
            Button btn_YearNumber = (Button) view.findViewById(R.id.btn_YearNumber);
            TextView txt_Id = (TextView) view.findViewById(R.id.txt_Id);
            TextView txt_Begin = (TextView) view.findViewById(R.id.txt_Begin);
            TextView txt_Finish = (TextView) view.findViewById(R.id.txt_Finish);
            TextView txt_CompanyName = (TextView) view.findViewById(R.id.txt_CompanyName);
            TextView txt_Time = (TextView) view.findViewById(R.id.txt_Time);
            TextView txt_Title = (TextView) view.findViewById(R.id.txt_Title);

            float YEAR = 365 * 24 * 60 * 60 * 1000;
            btn_YearNumber.setText((lstWorkExp.get(i).getFinish() - lstWorkExp.get(i).getBegin()) / YEAR + "");
            txt_Id.setText(lstWorkExp.get(i).getId() + "");
            txt_Begin.setText(lstWorkExp.get(i).getBegin() + "");
            txt_Finish.setText(lstWorkExp.get(i).getFinish() + "");
            txt_CompanyName.setText(lstWorkExp.get(i).getCompanyName());
            SimpleDateFormat sdf = new SimpleDateFormat("MM/yyyy");
            txt_Time.setText("Từ " + sdf.format(new Date(lstWorkExp.get(i).getBegin()))
                    + " đến " + sdf.format(new Date(lstWorkExp.get(i).getFinish())));
            txt_Title.setText(lstWorkExp.get(i).getTitle());

            ln_Parent.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    LinearLayout parent = (LinearLayout) v.getParent();
                    TextView txt_Id = (TextView) parent.findViewById(R.id.txt_Id);
                    TextView txt_Begin = (TextView) parent.findViewById(R.id.txt_Begin);
                    TextView txt_Finish = (TextView) parent.findViewById(R.id.txt_Finish);
                    TextView txt_CompanyName = (TextView) parent.findViewById(R.id.txt_CompanyName);
                    TextView txt_Title = (TextView) parent.findViewById(R.id.txt_Title);

                    Intent intent = new Intent(UVRegisterActivity.this, UVWorkExpActivity.class);
                    intent.putExtra(UVWorkExpActivity.ID, txt_Id.getText().toString());
                    intent.putExtra(UVWorkExpActivity.BEGIN, txt_Begin.getText().toString());
                    intent.putExtra(UVWorkExpActivity.FINISH, txt_Finish.getText().toString());
                    intent.putExtra(UVWorkExpActivity.COMPANYNAME, txt_CompanyName.getText().toString());
                    intent.putExtra(UVWorkExpActivity.TITLE, txt_Title.getText().toString());

                    startActivityForResult(intent, RequestCode.ADD_WORKEXP);
                }
            });

            ln_Remove.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    LinearLayout parent = (LinearLayout) v.getParent();
                    TextView txt_Id = (TextView) parent.findViewById(R.id.txt_Id);
                    try {
                        Long Id = Long.parseLong(txt_Id.getText().toString());
                        for (int i = 0; i < lstWorkExp.size(); i++)
                            if (lstWorkExp.get(i).getId().equals(Id)) {
                                lstWorkExp.remove(i);
                                break;
                            }
                        loadWorkExp();
                    } catch (Exception ex) {

                    }
                }
            });
            vg_WorkExp.addView(view, 0, new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,
                    ViewGroup.LayoutParams.MATCH_PARENT));
        }
    }

    private void addView() {
        imgv_Back = (ImageView) findViewById(R.id.imgv_Back);
        imgv_RemoveCV = (ImageView) findViewById(R.id.imgv_RemoveCV);
        btn_Register = (Button) findViewById(R.id.btn_Register);

        txt_BirthDay = (EditText) findViewById(R.id.txt_BirthDay);
        txt_Gender = (EditText) findViewById(R.id.txt_Gender);
        txt_Email = (EditText) findViewById(R.id.txt_Email);
        txt_Password = (EditText) findViewById(R.id.txt_Password);
        txt_ConfirmPassword = (EditText) findViewById(R.id.txt_ConfirmPassword);
        txt_FullName = (EditText) findViewById(R.id.txt_FullName);
        txt_Description = (EditText) findViewById(R.id.txt_Description);
        txt_Tag = (EditText) findViewById(R.id.txt_Tag);
        txt_CV = (EditText) findViewById(R.id.txt_CV);
        txt_Phone = (EditText) findViewById(R.id.txt_Phone);
        txt_FacebookURL = (EditText) findViewById(R.id.txt_FacebookURL);
        txt_Address = (EditText) findViewById(R.id.txt_Address);

        txt_WorkType = (TagsEditText) findViewById(R.id.txt_WorkType);
        txt_Career = (TagsEditText) findViewById(R.id.txt_Career);
        txt_Level = (TagsEditText) findViewById(R.id.txt_Level);
        txt_Experience = (TagsEditText) findViewById(R.id.txt_Experience);
        txt_Salary = (TagsEditText) findViewById(R.id.txt_Salary);
        txt_WorkPlace = (TagsEditText) findViewById(R.id.txt_WorkPlace);

        btn_AddWorkType = (Button) findViewById(R.id.btn_AddWorkType);
        btn_AddCareer = (Button) findViewById(R.id.btn_AddCareer);
        btn_AddLevel = (Button) findViewById(R.id.btn_AddLevel);
        btn_AddExperience = (Button) findViewById(R.id.btn_AddExperience);
        btn_AddSalary = (Button) findViewById(R.id.btn_AddSalary);
        btn_AddWorkPlace = (Button) findViewById(R.id.btn_AddWorkPlace);

        btn_AddWorkExps = (Button) findViewById(R.id.btn_AddWorkExps);
        vg_WorkExp = (ViewGroup) findViewById(R.id.ln_WorkExps);

        btn_AddDiplomas = (Button) findViewById(R.id.btn_AddDiplomas);
        vg_Diploma = (ViewGroup) findViewById(R.id.ln_Diplomas);
    }

    private void addEvent() {
        btn_AddWorkExps.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(UVRegisterActivity.this, UVWorkExpActivity.class);
                startActivityForResult(intent, RequestCode.ADD_WORKEXP);
            }
        });

        btn_AddDiplomas.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(UVRegisterActivity.this, UVDiplomaActivity.class);
                startActivityForResult(intent, RequestCode.ADD_DIPLOMA);
            }
        });

        txt_BirthDay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showDatePickerDialog();
            }
        });
        txt_Gender.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showChoiceGenderDialog();
            }
        });
        txt_CV.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showFileChooser();
            }
        });
        btn_Register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Register();
            }
        });
        imgv_Back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
        imgv_RemoveCV.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                CVUri = null;
                txt_CV.setText("");
                imgv_RemoveCV.setVisibility(View.GONE);
            }
        });

        btn_AddWorkType.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivitySelectForResult(SelectWorkTypeActivity.class, txt_WorkType, "lstWorkTypeSelected", RequestCode.SELECT_WORKTYPE);
            }
        });
        btn_AddCareer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivitySelectForResult(SelectCarrerActivity.class, txt_Career, "lstCareerSelected", RequestCode.SELECT_CAREER);
            }
        });
        btn_AddLevel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivitySelectForResult(SelectLevelActivity.class, txt_Level, "lstLevelSelected", RequestCode.SELECT_LEVEL);
            }
        });
        btn_AddExperience.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivitySelectForResult(SelectExperienceActivity.class, txt_Experience, "lstExperienceSelected", RequestCode.SELECT_EXPERIENCE);
            }
        });
        btn_AddSalary.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivitySelectForResult(SelectSalaryActivity.class, txt_Salary, "lstSalarySelected", RequestCode.SELECT_SALARY);
            }
        });
        btn_AddWorkPlace.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivitySelectForResult(SelectWorkPlaceActivity.class, txt_WorkPlace, "lstWorkPlaceSelected", RequestCode.SELECT_WORKPLACE);
            }
        });
    }

    private void startActivitySelectForResult(Class selectScreen, TagsEditText tags, String keyData, int requestCode) {
        Intent intent = new Intent(this, selectScreen);
        String data = tags.getTags().toString().substring(1, tags.getTags().toString().length() - 1);
        intent.putExtra(keyData, data);
        startActivityForResult(intent, requestCode);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        switch (requestCode) {
            case RequestCode.SELECT_WORKTYPE:
                if (data != null) {
                    String message = data.getStringExtra("lstWorkType");
                    txt_WorkType.setTags(message.split(","));
                }
                break;
            case RequestCode.SELECT_CAREER:
                if (data != null) {
                    String message = data.getStringExtra("lstCareer");
                    txt_Career.setTags(message.split(","));
                }
                break;
            case RequestCode.SELECT_LEVEL:
                if (data != null) {
                    String message = data.getStringExtra("lstLevel");
                    txt_Level.setTags(message.split(","));
                }
                break;
            case RequestCode.SELECT_EXPERIENCE:
                if (data != null) {
                    String message = data.getStringExtra("lstExperience");
                    txt_Experience.setTags(message.split(","));
                }
                break;
            case RequestCode.SELECT_SALARY:
                if (data != null) {
                    String message = data.getStringExtra("lstSalary");
                    txt_Salary.setTags(message.split(","));
                }
                break;
            case RequestCode.SELECT_WORKPLACE:
                if (data != null) {
                    String message = data.getStringExtra("lstWorkPlace");
                    txt_WorkPlace.setTags(message.split(","));
                }
                break;
            case RequestCode.ADD_WORKEXP:
                if (data != null) {
                    boolean isUpdate = data.getBooleanExtra(UVWorkExpActivity.IS_UPDATE, false);
                    if (isUpdate) {
                        Long Id = data.getLongExtra(UVWorkExpActivity.ID, -1L);
                        if (Id != -1L) {
                            for (int i = 0; i < lstWorkExp.size(); i++) {
                                if (lstWorkExp.get(i).getId().equals(Id)) {
                                    lstWorkExp.remove(i);
                                    break;
                                }
                            }
                        }
                    }
                    WorkExp we = new WorkExp();
                    we.setId((new Date()).getTime());
                    we.setCompanyName(data.getStringExtra(UVWorkExpActivity.COMPANYNAME));
                    we.setTitle(data.getStringExtra(UVWorkExpActivity.TITLE));
                    we.setBegin(data.getLongExtra(UVWorkExpActivity.BEGIN, 0));
                    we.setFinish(data.getLongExtra(UVWorkExpActivity.FINISH, 0));

                    lstWorkExp.add(we);
                    loadWorkExp();
                }
                break;
            case RequestCode.ADD_DIPLOMA:
                if (data != null) {
                    boolean isUpdate = data.getBooleanExtra(UVDiplomaActivity.IS_UPDATE, false);
                    if (isUpdate) {
                        Long Id = data.getLongExtra(UVDiplomaActivity.ID, -1L);
                        if (Id != -1L) {
                            for (int i = 0; i < lstDiploma.size(); i++) {
                                if (lstDiploma.get(i).getId().equals(Id)) {
                                    lstDiploma.remove(i);
                                    break;
                                }
                            }
                        }
                    }
                    Diploma dp = new Diploma();
                    dp.setId((new Date()).getTime());
                    dp.setName(data.getStringExtra(UVDiplomaActivity.NAME));
                    dp.setIssuedBy(data.getStringExtra(UVDiplomaActivity.ISSUEDBY));
                    dp.setIssuedDate(data.getLongExtra(UVDiplomaActivity.ISSUEDDATE, 0));
                    dp.setScores(data.getFloatExtra(UVDiplomaActivity.SCORES, 0));
                    dp.setRanking(data.getStringExtra(UVDiplomaActivity.RANKING));

                    lstDiploma.add(dp);
                    loadDiploma();
                }
                break;
            case RequestCode.FILE_SELECT_CODE:
                if (resultCode == RESULT_OK) {
                    imgv_RemoveCV.setVisibility(View.VISIBLE);
                    // Get the Uri of the selected file
                    CVUri = data.getData();
                    Log.d("FileUri", "File Uri: " + CVUri.toString());
                    // Get the path
                    String path = CVUri.getPath();
                    String[] arr = path.split("/");
                    txt_CV.setText(arr[arr.length - 1]);
                    Log.d("FilePath", "File Path: " + path);
                }
                break;
        }
    }

    private void setIcon() {
        btn_AddWorkType.setTypeface(FontManager.getTypeface(this, FontManager.FONTAWESOME));
        btn_AddCareer.setTypeface(FontManager.getTypeface(this, FontManager.FONTAWESOME));
        btn_AddLevel.setTypeface(FontManager.getTypeface(this, FontManager.FONTAWESOME));
        btn_AddExperience.setTypeface(FontManager.getTypeface(this, FontManager.FONTAWESOME));
        btn_AddSalary.setTypeface(FontManager.getTypeface(this, FontManager.FONTAWESOME));
        btn_AddWorkPlace.setTypeface(FontManager.getTypeface(this, FontManager.FONTAWESOME));
    }

    private void showDatePickerDialog() {
        DatePickerDialog.OnDateSetListener callback = new DatePickerDialog.OnDateSetListener() {
            public void onDateSet(DatePicker view, int year,
                                  int monthOfYear,
                                  int dayOfMonth) {
                //Mỗi lần thay đổi ngày tháng năm thì cập nhật lại TextView
                txt_BirthDay.setText((dayOfMonth) + "/" + (monthOfYear + 1) + "/" + year);
                //Lưu vết lại biến ngày hoàn thành
                cal.set(year, monthOfYear, dayOfMonth);
            }
        };
        //các lệnh dưới này xử lý ngày giờ trong DatePickerDialog
        //sẽ giống với trên TextView khi mở nó lên
        if (txt_BirthDay.getText().length() == 0) {
            SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
            txt_BirthDay.setText(sdf.format(new Date()));
        }
        String s = txt_BirthDay.getText() + "";
        String strArrtmp[] = s.split("/");
        int ngay = Integer.parseInt(strArrtmp[0]);
        int thang = Integer.parseInt(strArrtmp[1]) - 1;
        int nam = Integer.parseInt(strArrtmp[2]);
        DatePickerDialog pic = new DatePickerDialog(
                this,
                callback, nam, thang, ngay);
        pic.setTitle("Chọn ngày sinh của bạn");
        pic.show();
    }

    private void showChoiceGenderDialog() {
        // khởi tạo dialog
        final Dialog dialog = new Dialog(this);
        // xét layout cho dialog
        dialog.setContentView(R.layout.dialog_choice_gender);
        // xét tiêu đề cho dialog
        dialog.setTitle("Chọn giới tính của bạn");
        // khai báo control trong dialog để bắt sự kiện
        Button btn_Male = (Button) dialog.findViewById(R.id.btn_Male);
        Button btn_Female = (Button) dialog.findViewById(R.id.btn_Female);
        // bắt sự kiện
        btn_Male.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                txt_Gender.setText("Nam");
                dialog.dismiss();
            }
        });
        btn_Female.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                txt_Gender.setText("Nữ");
                dialog.dismiss();
            }
        });
        // hiển thị dialog
        dialog.show();
    }

    private void showFileChooser() {
        Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
        intent.setType("*/*");
        intent.addCategory(Intent.CATEGORY_OPENABLE);
        try {
            startActivityForResult(Intent.createChooser(intent, "Select a File to Upload"), RequestCode.FILE_SELECT_CODE);
        } catch (android.content.ActivityNotFoundException ex) {
            // Potentially direct the user to the Market with a Dialog
            Toast.makeText(this, "Please install a File Manager.", Toast.LENGTH_SHORT).show();
        }
    }
}
