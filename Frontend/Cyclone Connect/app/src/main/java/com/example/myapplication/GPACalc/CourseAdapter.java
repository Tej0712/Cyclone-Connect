package com.example.myapplication.GPACalc;

import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.example.myapplication.R;
import java.util.List;

public class CourseAdapter extends RecyclerView.Adapter<CourseAdapter.CourseViewHolder> {
    private final List<Course> courseList;
    private final GPACalculatorActivity activity;

    public CourseAdapter(List<Course> courseList, GPACalculatorActivity activity) {
        this.courseList = courseList;
        this.activity = activity;
    }

    @NonNull
    @Override
    public CourseViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_course, parent, false);
        return new CourseViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull CourseViewHolder holder, int position) {
        Course course = courseList.get(position);
        holder.bind(course);
    }

    @Override
    public int getItemCount() {
        return courseList.size();
    }

    public class CourseViewHolder extends RecyclerView.ViewHolder {
        TextView editTextCourseName;
        TextView textViewCredits;
        Spinner spinnerGrade;
        TextView textViewCardId;

        public CourseViewHolder(@NonNull View itemView) {
            super(itemView);
            editTextCourseName = itemView.findViewById(R.id.editTextCourseName);
            textViewCredits = itemView.findViewById(R.id.textViewCredits);
            spinnerGrade = itemView.findViewById(R.id.spinnerGrade);
            textViewCardId = itemView.findViewById(R.id.textViewCardId);
        }

        public void bind(Course course) {
            editTextCourseName.setText(course.getSubjectName());
            textViewCredits.setText(String.valueOf(course.getCredit()));
            spinnerGrade.setSelection(getGradePosition(course.getGrade()));
            textViewCardId.setText(String.valueOf(course.getId()));



            spinnerGrade.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                @Override
                public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                    String selectedGrade = parent.getItemAtPosition(position).toString();
                    course.setGrade(selectedGrade);
                    notifyDataSetChanged();

                }

                @Override
                public void onNothingSelected(AdapterView<?> parent) {
                }
            });
        }

        private int getGradePosition(String grade) {
            String[] grades = activity.getResources().getStringArray(R.array.grades_array);
            for (int i = 0; i < grades.length; i++) {
                if (grades[i].equals(grade)) {
                    return i;
                }
            }
            return 0;
        }
    }
}