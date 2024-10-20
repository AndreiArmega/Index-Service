import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class AdvancedTokenizer implements Tokenizer {
    @Override
    public List<String> tokenize(String content) {
        List<String> tokens = new ArrayList<>();

        // Define regex patterns for various types of tokens
        Pattern pattern = Pattern.compile("[\\w'-]+(?:[!?.;,:()\"'#$%&*]+[\\w'-]*)*|[!?.;,:()\"'#$%&*]+|\\d+|\\S+@[\\w-]+\\.[a-zA-Z]+");
        Matcher matcher = pattern.matcher(content);

        while (matcher.find()) {
            tokens.add(matcher.group());
        }

        return tokens;
    }
}
