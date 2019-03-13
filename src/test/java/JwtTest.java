import com.xusenme.model.User;
import com.xusenme.utils.JwtUtil;
import io.jsonwebtoken.Claims;
import org.junit.Test;

public class JwtTest {

    @Test
    public void test1() {
        String jwt = "eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJ4dXNlbiIsInNpemUiOjAsImFkbWluIjp0cnVlLCJpZCI6ImExOTY2MzMxLWM3ZDItNDQwYS1iMjAwLTE1NTAzZTUyNmVkZSIsImV4cCI6MTU1MjM4OTUzOCwiaWF0IjoxNTUyMzAzMTM4LCJlbWFpbCI6Inh1c2VubWVAMTYzLmNvbSIsImp0aSI6IjFhOGE1MDdiLTBiNjgtNDYwMC1iYTEwLWZiZmQxZjgwZjc3NCIsInVzZXJuYW1lIjoieHVzZW4ifQ.3HnPqFBD-TaeQgg21ZUwvWzCPoAiOLXs9zsg4I_rBZk";
        Claims claim = JwtUtil.parseJWT(jwt, "haha");
        System.out.println(claim);
    }
}
