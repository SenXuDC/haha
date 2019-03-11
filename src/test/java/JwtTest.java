import com.xusenme.model.User;
import com.xusenme.utils.JwtUtil;
import io.jsonwebtoken.Claims;
import org.junit.Test;

public class JwtTest {

    @Test
    public void test1() {
        String jwt = "eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJ4dXNlbiIsInBhc3N3b3JkIjoieHVzZW4iLCJzaXplIjowLCJhZG1pbiI6ZmFsc2UsImlkIjoiYTE5NjYzMzEtYzdkMi00NDBhLWIyMDAtMTU1MDNlNTI2ZWRlIiwiZXhwIjoxNTUyMzg1NDMxLCJpYXQiOjE1NTIyOTkwMzEsImVtYWlsIjoieHVzZW5tZUAxNjMuY29tIiwianRpIjoiYjI2MGIzZWEtMjRmYi00ZThlLWE5NDMtYmNkMDZiMDg0MTM5IiwidXNlcm5hbWUiOiJ4dXNlbiJ9.YAm1uQt3GGwZkyhv7tJ9EPuAlkYsk3t4GQH8G5YN_Ss";
        Claims claim = JwtUtil.parseJWT(jwt, "haha");
        System.out.println(claim);
    }
}
