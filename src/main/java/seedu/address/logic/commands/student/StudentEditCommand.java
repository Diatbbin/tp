package seedu.address.logic.commands.student;

import static java.util.Objects.requireNonNull;
import static seedu.address.logic.parser.CliSyntax.PREFIX_ADDRESS;
import static seedu.address.logic.parser.CliSyntax.PREFIX_ATTENDANCE;
import static seedu.address.logic.parser.CliSyntax.PREFIX_CCA;
import static seedu.address.logic.parser.CliSyntax.PREFIX_COMMENT;
import static seedu.address.logic.parser.CliSyntax.PREFIX_EMAILSTUDENT;
import static seedu.address.logic.parser.CliSyntax.PREFIX_IMAGESTUDENT;
import static seedu.address.logic.parser.CliSyntax.PREFIX_INDEXNUMBER;
import static seedu.address.logic.parser.CliSyntax.PREFIX_NEWCLASS;
import static seedu.address.logic.parser.CliSyntax.PREFIX_NEWINDEXNUMBER;
import static seedu.address.logic.parser.CliSyntax.PREFIX_NEWNAME;
import static seedu.address.logic.parser.CliSyntax.PREFIX_PARENTNAME;
import static seedu.address.logic.parser.CliSyntax.PREFIX_PHONEPARENT;
import static seedu.address.logic.parser.CliSyntax.PREFIX_PHONESTUDENT;
import static seedu.address.logic.parser.CliSyntax.PREFIX_RELATIONSHIP;
import static seedu.address.logic.parser.CliSyntax.PREFIX_SEX;
import static seedu.address.logic.parser.CliSyntax.PREFIX_STUDENTAGE;
import static seedu.address.model.Model.PREDICATE_SHOW_ALL_PARENTS;
import static seedu.address.model.Model.PREDICATE_SHOW_ALL_STUDENTS;

import java.util.List;
import java.util.Set;

import javafx.collections.ObservableList;
import seedu.address.commons.core.Messages;
import seedu.address.logic.commands.CommandResult;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.person.Address;
import seedu.address.model.person.Age;
import seedu.address.model.person.Class;
import seedu.address.model.person.Comment;
import seedu.address.model.person.Email;
import seedu.address.model.person.Image;
import seedu.address.model.person.Name;
import seedu.address.model.person.Phone;
import seedu.address.model.person.Sex;
import seedu.address.model.person.parent.Parent;
import seedu.address.model.person.parent.Relationship;
import seedu.address.model.person.student.Attendance;
import seedu.address.model.person.student.Cca;
import seedu.address.model.person.student.Homework;
import seedu.address.model.person.student.IndexNumber;
import seedu.address.model.person.student.Student;
import seedu.address.model.person.student.Test;
import seedu.address.model.tag.Tag;

/**
 * Edits the details of an existing person in the address book.
 */
public class StudentEditCommand extends StudentCommand {

    public static final String COMMAND_WORD = "edit";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Edits the details of the student identified by their "
            + "index number and class in the displayed student list"
            + "Parameters: "
            + PREFIX_INDEXNUMBER + "INDEX NUMBER "
            + "["
            + PREFIX_NEWNAME + "NEW NAME "
            + PREFIX_NEWINDEXNUMBER + "NEW INDEX NUMBER"
            + PREFIX_NEWCLASS + "NEW STUDENT CLASS"
            + PREFIX_SEX + "SEX"
            + PREFIX_STUDENTAGE + "AGE"
            + PREFIX_IMAGESTUDENT + "IMAGE STUDENT"
            + PREFIX_CCA + "CCA"
            + PREFIX_ATTENDANCE + "ATTENDANCE"
            + PREFIX_COMMENT + "COMMENT"
            + PREFIX_PHONESTUDENT + "STUDENT NUMBER"
            + PREFIX_EMAILSTUDENT + "STUDENT EMAIL"
            + PREFIX_ADDRESS + "ADDRESS"
            + PREFIX_PARENTNAME + "PARENT NAME"
            + PREFIX_PHONEPARENT + "PARENT PHONE NUMBER"
            + PREFIX_RELATIONSHIP + "RELATIONSHIP"
            + "]\n"
            + "Example: \n" + "student 1A " + COMMAND_WORD + " "
            + PREFIX_INDEXNUMBER + "02 "
            + PREFIX_NEWNAME + "Tan Ah Niu "
            + PREFIX_NEWINDEXNUMBER + "03 "
            + PREFIX_NEWCLASS + "1B "
            + PREFIX_SEX + "M"
            + PREFIX_STUDENTAGE + "10 "
            + PREFIX_IMAGESTUDENT + "C:// "
            + PREFIX_CCA + "AIKIDO "
            + PREFIX_ATTENDANCE + "T "
            + PREFIX_COMMENT + "GOOD BOY "
            + PREFIX_PHONESTUDENT + "90909090 "
            + PREFIX_EMAILSTUDENT + "tanahcow@gmail.com "
            + PREFIX_ADDRESS + "Blk 245 Ang Mo Kio Avenue 1 #11-800 S(560245) "
            + PREFIX_PARENTNAME + "Tan Ah Seng "
            + PREFIX_PHONEPARENT + "98989898 "
            + PREFIX_RELATIONSHIP + "FATHER";

    public static final String MESSAGE_EDIT_STUDENT_SUCCESS = "Edited student: %1$s";

    private IndexNumber indexNumber;
    private IndexNumber newIndexNumber;
    private Class studentClass;
    private Class newStudentClass;
    private Name newStudentName;
    private Age newAge;
    private Image newImage;
    private Cca newCca;
    private Attendance newAttendance;
    private Comment newComment;
    private Phone newStudentPhoneNumber;
    private Email newEmail;
    private Address newAddress;
    private Set<Tag> newTagList;
    private Set<Test> newTest;
    private Set<Homework> newHomework;
    private Relationship newRelationship;
    private Name newName;
    private Sex newSex;
    private Phone newParentPhoneNumber;
    private Name newParentName;



    /**
     * @param indexNumber of the person in the filtered person list to edit
     */
    public StudentEditCommand(Name name, Name newName, IndexNumber indexNumber, IndexNumber newIndexNumber,
                              Class studentClass, Class newStudentClass, Sex newSex, Phone newParentPhoneNumber,
                              Name newParentName, Relationship newRelationship, Age newAge, Image newImage, Cca newCca,
                              Attendance newAttendance, Comment newComment, Phone newStudentPhoneNumber, Email newEmail,
                              Address newAddress, Set<Tag> newTagList) {
        requireNonNull(indexNumber);
        requireNonNull(studentClass);

        this.newName = newName;
        this.newRelationship = newRelationship;
        this.newParentName = newParentName;
        this.indexNumber = indexNumber;
        this.studentClass = studentClass;
        this.newParentPhoneNumber = newParentPhoneNumber;
        this.newAge = newAge;
        this.newImage = newImage;
        this.newCca = newCca;
        this.newAttendance = newAttendance;
        this.newComment = newComment;
        this.newStudentPhoneNumber = newStudentPhoneNumber;
        this.newEmail = newEmail;
        this.newAddress = newAddress;
        this.newSex = newSex;
        this.newTagList = newTagList;
        this.newIndexNumber = newIndexNumber;
        this.newStudentClass = newStudentClass;
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);
        List<Student> students = model.getPcClass().getClassList().get(0).getStudents().asUnmodifiableObservableList();
        for (int i = 0; i < model.getPcClass().getClassList().size(); i++) {
            if (model.getPcClass().getClassList().get(i).getClassName().equals(studentClass.getClassName())) {
                students = model.getPcClass().getClassList().get(i).getStudents().asUnmodifiableObservableList();
                break;
            }
        }

        for (Student student : students) {
            if (student.getIndexNumber().equals(indexNumber) && studentClass.equals(studentClass)) {
                if (Name.isDefaultName(newName.fullName)) {
                    this.newName = student.getName();
                }
                if (IndexNumber.isDefaultIndexNumber(newIndexNumber.value)) {
                    this.newIndexNumber = this.indexNumber;
                }
                if (Class.isDefaultClass(newStudentClass.getClassName())) {
                    this.newStudentClass = this.studentClass;
                }
                if (Phone.isDefaultPhone(newParentPhoneNumber.value)) {
                    this.newParentPhoneNumber = student.getParentNumber();
                }
                if (Phone.isDefaultPhone(newStudentPhoneNumber.value)) {
                    this.newStudentPhoneNumber = student.getPhone();
                }
                if (Age.isDefaultAge(newAge.value)) {
                    this.newAge = student.getAge();
                }
                if (Image.isDefaultImage(newImage.value)) {
                    this.newImage = student.getImage();
                }
                if (Cca.isDefaultCca(newCca.value)) {
                    this.newCca = student.getCca();
                }
                if (Attendance.isDefaultAttendance(newAttendance.value)) {
                    this.newAttendance = student.getAttendance();
                }
                if (Comment.isDefaultComment(newComment.value)) {
                    this.newComment = student.getComment();
                }
                if (Image.isDefaultImage(newImage.value)) {
                    this.newImage = student.getImage();
                }
                if (Email.isDefaultEmail(newEmail.value)) {
                    this.newEmail = student.getEmail();
                }
                if (Address.isDefaultAddress(newAddress.value)) {
                    this.newAddress = student.getAddress();
                }
                if (Sex.isDefaultSex(newSex.value)) {
                    this.newSex = student.getSex();
                }
                if (Relationship.isDefaultRelationship(newRelationship.rls)) {
                    this.newRelationship = student.getRls();
                }
                if (Name.isDefaultName(newParentName.fullName)) {
                    this.newParentName = student.getParentName();
                }

                this.newTagList = student.getTagList();
                this.newTest = student.getTest();
                this.newHomework = student.getHomeworkSet();
                this.newName = student.getName();

                Student newStudent = new Student(newName, this.newStudentClass, this.newIndexNumber, this.newSex,
                        this.newParentName, this.newParentPhoneNumber, this.newRelationship, this.newAge, this.newImage,
                        this.newEmail, this.newStudentPhoneNumber, this.newCca, this.newAddress, this.newAttendance,
                        newHomework, this.newTest, this.newTagList, this.newComment);

                model.setStudent(student, editStudent(model, student, newStudent));
                model.updateFilteredStudentList(PREDICATE_SHOW_ALL_STUDENTS);
                return new CommandResult(String.format(MESSAGE_EDIT_STUDENT_SUCCESS, newStudent));
            }
        }

        throw new CommandException(Messages.MESSAGE_INVALID_STUDENT_DISPLAYED);
    }

    /**
     * Method that helps transfer and update all students in the original Parent object to the new EDITED Parent object.
     *
     * @param studentToEdit Original Parent object that is to be edited.
     * @param newStudent Edited Parent object.
     * @return Edited Parent object with list of students in original Parent object and updates all the students.
     */
    private Student editStudent(Model model, Student studentToEdit, Student newStudent) {

        Phone oldParentNumber = studentToEdit.getParentNumber();

        if (oldParentNumber == newStudent.getParentNumber()) {
            return newStudent;
        } else {
            ObservableList<Parent> parents = model.getFilteredParentList();
            ObservableList<Student> students = model.getFilteredStudentList();
            Parent parentToEdit = null;
            for (Parent parent : parents) {
                if (parent.getPhone() == studentToEdit.getParentNumber()) {
                    parentToEdit = parent;
                }
            }

            if (parentToEdit != null) {
                parentToEdit.setPhone(newStudent.getParentNumber());
            }

            for (Student student : students) {
                if (student.getPhone() == studentToEdit.getParentNumber()) {
                    student.setPhone(newStudent.getParentNumber());
                }
            }
            model.updateFilteredStudentList(PREDICATE_SHOW_ALL_STUDENTS);
            model.updateFilteredParentList(PREDICATE_SHOW_ALL_PARENTS);
        }
        return newStudent;
    }
}