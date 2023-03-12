package seedu.address.logic.parser;

import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.parser.CliSyntax.*;

import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Stream;

import seedu.address.commons.core.index.Index;
import seedu.address.logic.commands.DeleteCommand;
import seedu.address.logic.commands.student.StudentAddCommand;
import seedu.address.logic.commands.student.StudentCommand;
import seedu.address.logic.commands.student.StudentDeleteCommand;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.person.Address;
import seedu.address.model.person.Age;
import seedu.address.model.person.Email;
import seedu.address.model.person.Image;
import seedu.address.model.person.Name;
import seedu.address.model.person.Phone;
import seedu.address.model.person.Sex;
import seedu.address.model.person.student.Attendance;
import seedu.address.model.person.student.Cca;
import seedu.address.model.person.student.Homework;
import seedu.address.model.person.student.IndexNumber;
import seedu.address.model.person.student.ParentName;
import seedu.address.model.person.student.Student;
import seedu.address.model.person.student.StudentClass;
import seedu.address.model.person.student.Test;
import seedu.address.model.tag.Tag;

/**
 * StudentCommandParser that parses commands starting with "student"
 */
public class StudentCommandParser implements Parser<StudentCommand> {

    public static final String HELP_MESSAGE = "Student command has to include a class and action.\n"
            + StudentCommand.MESSAGE_USAGE;
    private static final Pattern BASIC_COMMAND_FORMAT = Pattern.compile("(?<class>\\S+)(?<arguments>.*)");

    /**
     * Parse the command into their respective prefixes
     * @param args the command input by user
     * @return A StudentCommand
     * @throws ParseException
     */
    public StudentCommand parse(String args) throws ParseException {
        final Matcher matcher = BASIC_COMMAND_FORMAT.matcher(args.trim());
        if (!matcher.matches()) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, HELP_MESSAGE));
        }

        final String studentClass = matcher.group("class");
        final String arguments = matcher.group("arguments");

        ArgumentMultimap argMultimapAdd =
                ArgumentTokenizer.tokenize(arguments, PREFIX_ADD, PREFIX_NAME, PREFIX_INDEXNUMBER, PREFIX_SEX,
                        PREFIX_PARENTNAME, PREFIX_AGESTUDENT, PREFIX_IMAGESTUDENT, PREFIX_EMAILSTUDENT,
                        PREFIX_PHONESTUDENT, PREFIX_CCA, PREFIX_TEST, PREFIX_ATTENDANCE, PREFIX_HOMEWORK);

        ArgumentMultimap argMultimapDelete =
                ArgumentTokenizer.tokenize(arguments, PREFIX_DELETE, PREFIX_NAME, PREFIX_INDEXNUMBER, PREFIX_SEX,
                        PREFIX_PARENTNAME, PREFIX_AGESTUDENT, PREFIX_IMAGESTUDENT, PREFIX_EMAILSTUDENT,
                        PREFIX_PHONESTUDENT, PREFIX_CCA, PREFIX_TEST, PREFIX_ATTENDANCE, PREFIX_HOMEWORK);
        if (argMultimapAdd.getValue(PREFIX_ADD).isPresent()) {
            return addCommand(studentClass, argMultimapAdd);
        } else if (argMultimapDelete.getValue(PREFIX_DELETE).isPresent()) {
            return deleteCommand(studentClass, argMultimapDelete);
        } else {
            //Rest of logic (Need to edit)
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, HELP_MESSAGE));
        }

    }

    /**
     * Function to parse the "student class add" command
     * @param studentClass class of student
     * @param argMultimap mapper for each prefix
     * @return A StudentAddCommand
     * @throws ParseException
     */
    private StudentAddCommand addCommand(String studentClass, ArgumentMultimap argMultimap) throws ParseException {
        if (!arePrefixesPresent(argMultimap, PREFIX_NAME, PREFIX_INDEXNUMBER, PREFIX_SEX)
                || !argMultimap.getPreamble().isEmpty()
                || studentClass.length() == 0) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, StudentAddCommand.MESSAGE_USAGE));
        }
        StudentClass sc = ParserUtil.parseStudentClass(studentClass);
        Name name = ParserUtil.parseName(argMultimap.getValue(PREFIX_NAME).get());
        IndexNumber indexNumber = ParserUtil.parseIndexNumber(argMultimap.getValue(PREFIX_INDEXNUMBER).get());
        Sex sex = ParserUtil.parseSex(argMultimap.getValue(PREFIX_SEX).get());
        ParentName parentName = ParserUtil.parseParentName(argMultimap.getValue(PREFIX_PARENTNAME).get());
        Age age = ParserUtil.parseAge((argMultimap.getValue(PREFIX_AGESTUDENT).get()));
        Image image = ParserUtil.parseImage(argMultimap.getValue(PREFIX_IMAGESTUDENT).get());
        Email email = ParserUtil.parseEmail(argMultimap.getValue(PREFIX_EMAILSTUDENT).get());
        Phone phone = ParserUtil.parsePhone(argMultimap.getValue(PREFIX_PHONESTUDENT).get());
        Cca cca = ParserUtil.parseCca(argMultimap.getValue(PREFIX_CCA).get());
        Address address = ParserUtil.parseAddress(argMultimap.getValue(PREFIX_ADDRESS).get());
        Attendance attendance = ParserUtil.parseAttendance(argMultimap.getValue(PREFIX_ATTENDANCE).get());
        Homework homework = ParserUtil.parseHomework(argMultimap.getValue(PREFIX_HOMEWORK).get());
        Test test = ParserUtil.parseTest(argMultimap.getValue(PREFIX_TEST).get());
        Set<Tag> tagList = ParserUtil.parseTags(argMultimap.getAllValues(PREFIX_TAG));

        Student student = new Student(name, sc, indexNumber, sex, parentName, age, image, email, phone, cca, address,
                attendance, homework, test, tagList);

        return new StudentAddCommand(student);
    }

    /**
     * Parses {@code oneBasedIndex} into an {@code Index} and returns it. Leading and trailing whitespaces will be
     * trimmed.
     * @throws ParseException if the specified index is invalid (not non-zero unsigned integer).
     */
    public StudentDeleteCommand deleteCommand(String studentClass, ArgumentMultimap argMultimap) throws ParseException {
        try {
            StudentClass sc = ParserUtil.parseStudentClass(studentClass);
            IndexNumber indexNumber = ParserUtil.parseIndexNumber(argMultimap.getValue(PREFIX_INDEXNUMBER).get());
            return new StudentDeleteCommand(indexNumber, sc);
        } catch (ParseException pe) {
            throw new ParseException(
                    String.format(MESSAGE_INVALID_COMMAND_FORMAT, DeleteCommand.MESSAGE_USAGE), pe);
        }
    }


    /**
     * Returns true if none of the prefixes contains empty {@code Optional} values in the given
     * {@code ArgumentMultimap}.
     */
    private static boolean arePrefixesPresent(ArgumentMultimap argumentMultimap, Prefix... prefixes) {
        return Stream.of(prefixes).allMatch(prefix -> argumentMultimap.getValue(prefix).isPresent());
    }
}