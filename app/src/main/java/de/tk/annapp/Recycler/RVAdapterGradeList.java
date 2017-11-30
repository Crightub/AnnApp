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

    public RVAdapterGradeList(Context _c, String _subjectName){
        c = _c;
        subjectManager = SubjectManager.getInstance();
        subjectName = _subjectName;
        grades = subjectManager.getSubjectByName(_subjectName).getAllGrades();
    }

    @Override
    public RecyclerVH onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(c).inflate(R.layout.item_grade_list, parent, false);
        return new RecyclerVH(v);
    }

    @Override
    public void onBindViewHolder(RecyclerVH holder, final int position) {
        holder.gradeTxt.setText("" + grades.get(position).grade);
        holder.expandableTextView.setText(grades.get(position).note + "\nWertung: " + grades.get(position).rating);

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
                createAlertDialog("Wertung", "Die Wertung ist der Betrag, der das Verhältnis zu den anderen Noten des selben Types bestimmt.\n\nz.B.:\nWertung: 2\nDie Note wird doppelt so stark einberechnet wie andere Noten.\n\nWertung: 0,5\nDie Note wird halb so stark einberechnet wie andere Noten.", 0);
            }
        });






        ad      .setTitle("Note bearbeiten - " + subject.name)
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
                            createAlertDialog("Achtung", "Bitte füllen Sie alle notwendigen Felder aus!", android.R.drawable.ic_dialog_alert);
                            return;
                        }

                        if(!ratingInput.getText().toString().isEmpty())
                            rating = Float.parseFloat(ratingInput.getText().toString());


                        /*Subject subject = subjectManager.getSubjectByName(subjectSelection.getSelectedItem().toString());
                        subject.addGrade(Integer.valueOf(gradeInput.getText().toString()), isWrittenBool, rating, note.getText().toString());*/

                        //TODO Edit your grade - "grade" is the grade you want to edit - "subject" is the mother (or father - you don't really know - wait it could be
                        //TODO something other as well now) of the grade



                        subjectManager.save(c, "subjects");
                    }
                })
                .setNegativeButton("Abbrechen", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        //Do nothing
                    }
                })
                .show();
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

    public void delete(Subject subject, Grade grade){

        AlertDialog.Builder builder;
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            builder = new AlertDialog.Builder(c, android.R.style.Theme_Material_Dialog_Alert);
        } else {
            builder = new AlertDialog.Builder(c);
        }
        builder.setTitle("Löschen?")
                .setMessage("Wollen Sie diese Note wirklich löschen?")
                .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        //TODO deleting the Grade "grade" you're already asked wheter you would like to delete or not
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
