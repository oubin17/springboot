package com.ob.modelexample.classify.enmu;

/**
 * @Author: oubin
 * @Date: 2019/10/13 10:31
 * @Description:
 */
public enum ClassifyEnum {

    STUDENT_CLASSIFY("StudentClassify"),

    TEACHER_CLASSIFY("TeacherClassify");

    private String classifyName;

    ClassifyEnum(String classifyName) {
        this.classifyName = classifyName;
    }

    public String getClassifyName() {
        return classifyName;
    }

    public void setClassifyName(String classifyName) {
        this.classifyName = classifyName;
    }
}
