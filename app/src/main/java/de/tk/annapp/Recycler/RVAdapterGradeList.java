package de.tk.annapp.Recycler;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Build;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.Spinner;
import android.widget.TextView;

import com.ms.square.android.expandabletextview.ExpandableTextView;

import java.security.KeyStore;
import java.util.ArrayList;

import de.tk.annapp.R;
import de.tk.annapp.Subject;
import de.tk.annapp.SubjectManager;
import de.tk.annapp.Grade;

import static android.R.layout.simple_spinner_dropdown_item;

public class RVAdapterGradeList extends RecyclerView.Adapter<RVAdapterGradeList.RecyclerVH> {

    Context c;
    private ArrayList<Grade> grades = new ArrayList<>();
    private SubjectManager subjectManager;
    private String subjectName;

    boolean isWrittenBool;
    AlertDialog adTrueDialog;

    public RVAdapterGradeList(Context _c, String _subjectName){
        c = _c;
        subjectManager = SubjectManager.getInstance();
        subjectName = _subjectName;
        grades = subjectManager.getSubjectByName(subjectName).getAllGrades();
    }

    @Override
    public RecyclerVH onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(c).inflate(R.layout.item_grade_list, parent, false);
        return new RecyclerVH(v);
    }

    @Override
    public void onBindViewHolder(RecyclerVH holder, final int position) {
        grades = subjectManager.getSubjectByName(subjectName).getAllGrades();
        holder.gradeTxt.setText(String.valueOf(grades.get(position).grade));

        if(!grades.get(position).note.isEmpty())
            holder.expandableTextView.setText(grades.get(position).note + "\n" +  c.getString(R.string.ratingList) + grades.get(position).rating);
        else
            holder.expandableTextView.setText(grades.get(position).note +  c.getString(R.string.ratingList) + grades.get(position).rating);

        holder.editButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                System.out.println("Create InputDialog...");
                createEditDialog(subjectManager.getSubjectByName(subjectName), grades.get(position));
            }
        });
    }

    @Override
    public int getItemCount() {
        return grades.size();
    }

    //Viewholder Class
    public class RecyclerVH extends RecyclerView.ViewHolder{
        TextView gradeTxt;
        ExpandableTextView expandableTextView;
        ImageButton editButton;

        public RecyclerVH(View itemView){
            super(itemView);
            editButton = itemView.findViewById(R.id.item_grade_deleteButton);
            expandableTextView = itemView.findViewById(R.id.expandable_text_view);
            gradeTxt = itemView.findViewById(R.id.item_grade_grade);
        }
    }




    public void createEditDialog(final Subject subject, final Grade grade){

        AlertDialog.Builder ad = new  AlertDialog.Builder(c);



        //View mView = getLayoutInflater().inflate(R.layout.fragment_grade_input, null);
        View mView = View.inflate(c, R.layout.fragment_grade_edit, null);

        final EditText gradeInput = (EditText) mView.findViewById(R.id.gradeInput);
        gradeInput.setText(String.valueOf(grade.grade));

        final  EditText ratingInput =(EditText) mView.findViewById(R.id.ratingInput);
        ratingInput.setText(String.valueOf(grade.rating));

        final EditText note = (EditText) mView.findViewById(R.id.note);
        note.setText(grade.note);

        final ImageView btnHelp = (ImageView) mView.findViewById(R.id.btnHelp);
        final Button btnExtra = (Button) mView.findViewById(R.id.btnExtra);
        final LinearLayout extraLayout = (LinearLayout) mView.findViewById(R.id.extraLayout);

        final Button btnDelete = (Button) mView.findViewById(R.id.btnDelete);
        final Button btnDeleteIcon = (Button) mView.findViewById(R.id.btnDeleteIcon);

        final RadioButton isWritten = mView.findViewById(R.id.isWritten);

        final RadioButton isNotWritten = mView.findViewById(R.id.isNotWritten);

        if(grade.iswritten)
            isWritten.setChecked(true);
        else
            isNotWritten.setChecked(true);


        btnDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                delete(subject, grade);
            }
        });

        btnDeleteIcon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                delete(subject, grade);
            }
        });



        btnExtra.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(extraLayout.getVisibility() != View.VISIBLE)
                    extraLayout.setVisibility(View.VISIBLE);
                else
                    extraLayout.setVisibility(View.GONE);
            }
        });

        btnHelp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                createAlertDialog(c.getString(R.string.rating), c.getString(R.string.ratingExplanation), 0);
            }
        });






        ad      .setTitle(c.getString(R.string.editGrade) + subject.name)
                .setView(mView)
                .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {

                        float rating = 1;

                        //testing which button is active for decision whether your Grade is written or whether it's not
                        if(isWritten.isChecked())
                            isWrittenBool = true;
                        else if(isNotWritten.isChecked())
                            isWrittenBool = false;

                        if(gradeInput.getText().toString().isEmpty()){
                            createAlertDialog(c.getString(R.string.warning), c.getString(R.string.warningMessage), android.R.drawable.ic_dialog_alert);
                            return;
                        }

                        if(!ratingInput.getText().toString().isEmpty())
                            rating = Float.parseFloat(ratingInput.getText().toString());


                        /*Subject subject = subjectManager.getSubjectByName(subjectSelection.getSelectedItem().toString());
                        subject.addGrade(Integer.valueOf(gradeInput.getText().toString()), isWrittenBool, rating, note.getText().toString());*/

                        subject.editGrade(grade, Integer.valueOf(gradeInput.getText().toString()), isWrittenBool, rating, note.getText().toString());
                        notifyItemChanged(grades.indexOf(grade));

                        subjectManager.save(c, "subjects");
                    }
                })
                .setNegativeButton(R.string.cancel, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        //Do nothing

                    }
                });

                adTrueDialog = ad.show();
    }

    void createAlertDialog(String title, String text, int ic){
        AlertDialog.Builder builder;
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            builder = new AlertDialog.Builder(c, android.R.style.Theme_Material_Dialog_Alert);
        } else {
            builder = new AlertDialog.Builder(c);
        }
        builder.setTitle(title)
                .setMessage(text)
                .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                    }
                })
                .setIcon(ic)
                .show();
    }

    public void delete(final Subject subject, final Grade grade){

        final AlertDialog.Builder builder;
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            builder = new AlertDialog.Builder(c, android.R.style.Theme_Material_Dialog_Alert);
        } else {
            builder = new AlertDialog.Builder(c);
        }
        builder.setTitle(R.string.deleteQuestion)
                .setMessage(c.getString(R.string.deleteQuestionMessage))
                .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {

                        notifyItemRemoved(grades.indexOf(grade));
                        notifyItemRangeChanged(grades.indexOf(grade), getItemCount());
                        subject.removeGrade(grade);
                        subjectManager.save(c,"subjects");

                        adTrueDialog.cancel();

                    }
                })
                .setNegativeButton(android.R.string.no, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        //I think - do Nothing - but if you want
                    }
                })
                .setIcon(android.R.drawable.ic_delete)
                .show();

    }
}
