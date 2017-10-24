package com.spkt.nguyenducnguu.jobstore.UV;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.spkt.nguyenducnguu.jobstore.Const.RequestCode;
import com.spkt.nguyenducnguu.jobstore.FontManager.FontManager;
import com.spkt.nguyenducnguu.jobstore.Models.Diploma;
import com.spkt.nguyenducnguu.jobstore.Models.Experience;
import com.spkt.nguyenducnguu.jobstore.Models.WorkExp;
import com.spkt.nguyenducnguu.jobstore.NTD.NTDRegisterActivity;
import com.spkt.nguyenducnguu.jobstore.R;
import com.spkt.nguyenducnguu.jobstore.SelectCarrerActivity;
import com.spkt.nguyenducnguu.jobstore.SelectExperienceActivity;
import com.spkt.nguyenducnguu.jobstore.SelectLevelActivity;
import com.spkt.nguyenducnguu.jobstore.SelectSalaryActivity;
import com.spkt.nguyenducnguu.jobstore.SelectWorkPlaceActivity;
import com.spkt.nguyenducnguu.jobstore.SelectWorkTypeActivity;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import mabbas007.tagsedittext.TagsEditText;

public class UVRegisterActivity extends AppCompatActivity {
    public List<WorkExp> lstWorkExp = new ArrayList<WorkExp>();
    public List<Diploma> lstDiploma = new ArrayList<Diploma>();
    Calendar cal = Calendar.getInstance();
    ImageView imgv_Back;
    Button btn_Register;
    TextView txt_BirthDay, txt_Gender;
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
    private boolean ValidateInputData()
    {
        return false;
    }
    private void Register()
    {
        if(!ValidateInputData()) return;

    }
    private void loadDiploma()
    {
        vg_Diploma.removeAllViews();
        for (int i = 0; i < lstDiploma.size(); i++)
        {
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
                    try
                    {
                        Long Id = Long.parseLong(txt_Id.getText().toString());
                        for (int i = 0; i < lstDiploma.size(); i++)
                            if(lstDiploma.get(i).getId().equals(Id))
                            {
                                lstDiploma.remove(i);
                                break;
                            }
                        loadDiploma();
                    }
                    catch (Exception ex)
                    {

                    }
                }
            });
            vg_Diploma.addView(view, 0, new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,
                    ViewGroup.LayoutParams.MATCH_PARENT));
        }
    }
    private void loadWorkExp()
    {
        vg_WorkExp.removeAllViews();
        for (int i = 0; i < lstWorkExp.size(); i++)
        {
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

            float YEAR=365*24*60*60*1000;
            btn_YearNumber.setText((lstWorkExp.get(i).getFinish()- lstWorkExp.get(i).getBegin())/YEAR + "");
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
                    try
                    {
                        Long Id = Long.parseLong(txt_Id.getText().toString());
                        for (int i = 0; i < lstWorkExp.size(); i++)
                            if(lstWorkExp.get(i).getId().equals(Id))
                            {
                                lstWorkExp.remove(i);
                                break;
                            }
                        loadWorkExp();
                    }
                    catch (Exception ex)
                    {

                    }
                }
            });
            vg_WorkExp.addView(view, 0, new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,
                    ViewGroup.LayoutParams.MATCH_PARENT));
        }
    }
    private void addView()
    {
        imgv_Back = (ImageView) findViewById(R.id.imgv_Back);
        btn_Register = (Button) findViewById(R.id.btn_Register);

        txt_BirthDay = (TextView) findViewById(R.id.txt_BirthDay);
        txt_Gender = (TextView) findViewById(R.id.txt_Gender);

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
    private void addEvent()
    {
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
                    if(isUpdate)
                    {
                        Long Id = data.getLongExtra(UVWorkExpActivity.ID, -1L);
                        if(Id != -1L)
                        {
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
                    if(isUpdate)
                    {
                        Long Id = data.getLongExtra(UVDiplomaActivity.ID, -1L);
                        if(Id != -1L)
                        {
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
    private void showChoiceGenderDialog()
    {
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
}
