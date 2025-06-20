package at.technikum.studybuddy.dto;

import at.technikum.studybuddy.entity.BoxComment;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public class BoxCommentDto {

    private Long id;

    @NotBlank
    @Size(min = 5, max = 2000) // to match the db-varchar length
    private String text;

    // no user as of now
    // t.odo: displaydate als string?

    private Long boxId ;


    BoxCommentDto() {
        super();
    }

    public BoxCommentDto(BoxComment boxComment) {
        this();
        if (boxComment != null) {
            this.id = boxComment.getId();
            this.text = boxComment.getText();
            if (boxComment.getBox() != null) {
                this.boxId = boxComment.getBox().getId();
            }
        }
    }

    public Object getUser() throws NoSuchFieldException, IllegalAccessException {
        Object result = new Object() {
            public String getName() { return "ihr username hier"+text; };
            public String getId() { return "the userId ist ... 15!"; }
            public String getClassNameCustom = "";
        };
        //result.getClassNameCustom = "hello";
        result.getClass().getDeclaredField("getClassNameCustom").set(result,"hello2");
        result.getClass().getDeclaredField("getClassNameCustom").set(result,result.getClass().toString());
        return result;
    }

    public record MyUserDtoRecord (
        String foo, String id
    ){};

    public MyUserDtoRecord getMyUserDtoRecord() {
        MyUserDtoRecord res = new MyUserDtoRecord("fooString", "idString");
        return res;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public Long getBoxId() {
        return boxId;
    }

    public void setBoxId(Long boxId) {
        this.boxId = boxId;
    }
}
