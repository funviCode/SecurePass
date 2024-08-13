import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.security.SecureRandom;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class SecurePass {

    private static final String LOWERCASE = "abcdefghijklmnopqrstuvwxyz";
    private static final String UPPERCASE = LOWERCASE.toUpperCase();
    private static final String NUMBER = "0123456789";
    private static final String OTHER_SYMBOL = "!@#$%^&*()_+-=[]{}|;:,.<>?";
    private static final String CHINESE_SYMBOL = "人山山水火土日日月风风雨木金田口手手足目耳心力大小上下左右中天地子女";

    private static final String PASSWORD_BASE = LOWERCASE + UPPERCASE + NUMBER + OTHER_SYMBOL;
    private static final String PASSWORD_BASE_CHINA = LOWERCASE + UPPERCASE + NUMBER + OTHER_SYMBOL + CHINESE_SYMBOL;

    private static final int PASSWORD_LENGTH = 12;
    private static final int PASSWORD_NUMBERS = 3;

    private static final SecureRandom random = new SecureRandom();

    //Пароль без Китайский символов
    public static String generatePassword() {
        StringBuilder password = new StringBuilder(PASSWORD_LENGTH);

        //Делаем так чтобы по 1 символу из разных категорий было минимум
        password.append(LOWERCASE.charAt(random.nextInt(LOWERCASE.length())));
        password.append(UPPERCASE.charAt(random.nextInt(UPPERCASE.length())));
        password.append(NUMBER.charAt(random.nextInt(NUMBER.length())));
        password.append(OTHER_SYMBOL.charAt(random.nextInt(OTHER_SYMBOL.length())));

        //Остальную длину пароля уже берем из общей базы символов
        for (int i = 4; i < PASSWORD_LENGTH; i++) {
            password.append(PASSWORD_BASE.charAt(random.nextInt(PASSWORD_BASE.length())));
        }

        //Чтобы первые 4 символа были не по порядку(LOWER,UPPER,NUMBER,OTHER), их нужно еще раз перемешать
        //password.toString() преобразует объект StringBuilder в объект String, toCharArray преобразует этот объект String в массив char[]
        List<Character> passwordShuffle = new ArrayList<>();
        for (char f : password.toString().toCharArray()) {
            passwordShuffle.add(f);
        }
        Collections.shuffle(passwordShuffle);

        //Соединяем все перемешанные символы
        StringBuilder finalPassword = new StringBuilder();
        for (char f : passwordShuffle) {
            finalPassword.append(f);
        }

        return finalPassword.toString();

    }

    //Пароль с Китайскими символами
    private static String generatePasswordChina() {
        StringBuilder passwordCh = new StringBuilder(PASSWORD_LENGTH);

        passwordCh.append(LOWERCASE.charAt(random.nextInt(LOWERCASE.length())));
        passwordCh.append(UPPERCASE.charAt(random.nextInt(UPPERCASE.length())));
        passwordCh.append(NUMBER.charAt(random.nextInt(NUMBER.length())));
        passwordCh.append(OTHER_SYMBOL.charAt(random.nextInt(OTHER_SYMBOL.length())));
        passwordCh.append(CHINESE_SYMBOL.charAt(random.nextInt(CHINESE_SYMBOL.length())));

        for (int i = 5; i < PASSWORD_LENGTH; i++) {
            passwordCh.append(PASSWORD_BASE_CHINA.charAt(random.nextInt(PASSWORD_BASE_CHINA.length())));
        }

        //Так же перемешиваем
        List<Character> listCH = new ArrayList<>();
        for (char f : passwordCh.toString().toCharArray()) {
            listCH.add(f);
        }
        Collections.shuffle(listCH);

        StringBuilder finalPasswordChina = new StringBuilder();
        for (char f : listCH) {
            finalPasswordChina.append(f);
        }
        return finalPasswordChina.toString();
    }


    public static void main(String[] args) {
        List<String> passwords = new ArrayList<>();

        for (int i = 0; i < PASSWORD_NUMBERS; i++) {
            passwords.add(generatePassword());
        }
        for (int i = 0; i < PASSWORD_NUMBERS; i++) {
            passwords.add(generatePasswordChina());
        }

        File file = new File("C:\\Users\\dima\\Desktop\\password.txt");
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(file))) {
            for (String password : passwords) {
                writer.write(password);
                writer.newLine();
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

    }
}
