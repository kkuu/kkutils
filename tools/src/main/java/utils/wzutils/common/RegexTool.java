package utils.wzutils.common;

import java.util.regex.Pattern;

/***
 *
 * 一个或多个汉字	^[\u0391-\uFFE5]+$
 邮政编码	^[1-9]\d{5}$
 QQ号码	^[1-9]\d{4,10}$
 邮箱	^[a-zA-Z_]{1,}[0-9]{0,}@(([a-zA-z0-9]-*){1,}\.){1,3}[a-zA-z\-]{1,}$
 用户名（字母开头 + 数字/字母/下划线）	^[A-Za-z][A-Za-z1-9_-]+$
 手机号码	^1[3|4|5|8][0-9]\d{8}$
 URL	^((http|https)://)?([\w-]+\.)+[\w-]+(/[\w-./?%&=]*)?$
 18位身份证号	^(\d{6})(18|19|20)?(\d{2})([01]\d)([0123]\d)(\d{3})(\d|X|x)?$
 */
public class RegexTool {

    public static boolean isMather(String input,String regex){
        if(StringTool.isEmpty(input)||StringTool.isEmpty(regex))return false;
        return Pattern.compile(regex).matcher(input).matches();
    }

    /***
     * 大小写 字母 数字
     * @param input
     * @return
     */
    public static boolean isPwdOk(String input){
        return isMather(input,"^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d)\\S{8,16}$");
    }
}
