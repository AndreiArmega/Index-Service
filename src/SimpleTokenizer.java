import java.util.Arrays;
import java.util.List;

class SimpleTokenizer implements Tokenizer {
    @Override
    public List<String> tokenize(String text) {
        return Arrays.asList(text.split("\\W+"));
    }
}