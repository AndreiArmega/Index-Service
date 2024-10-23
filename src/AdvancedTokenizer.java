import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class AdvancedTokenizer implements Tokenizer {

    @Override
    public List<String> tokenize(String content) {
        List<String> tokens = new ArrayList<>();
        //Regex logic
        Pattern simplePattern = Pattern.compile("[\\w]+|[!?.;,:()\"'#$%&*]+");
        matchAndAddTokens(simplePattern, content, tokens);

        Pattern intermediatePattern = Pattern.compile("[\\w'-]+(?:[!?.;,:()\"'#$%&*]+[\\w'-]*)*|[!?.;,:()\"'#$%&*]+");
        matchAndAddTokens(intermediatePattern, content, tokens);

        Pattern advancedPattern = Pattern.compile("\\w+|[^\\w\\s]+");
        matchAndAddTokens(advancedPattern, content, tokens);

        Pattern specialSymbolsPattern = Pattern.compile("[^\\w\\s]+");
        matchAndAddTokens(specialSymbolsPattern, content, tokens);

        Pattern symbolsFollowedByWordPattern = Pattern.compile("[#@%$&]+[\\w'-]+");
        matchAndAddTokens(symbolsFollowedByWordPattern, content, tokens);

        Pattern wordWithSymbolsPattern = Pattern.compile("[\\w'-]+[!?.;,:()\"'#$%&*]+[\\w'-]+");
        matchAndAddTokens(wordWithSymbolsPattern, content, tokens);

        if (tokens.isEmpty()) {
            Pattern fallbackPattern = Pattern.compile(".*");  // Matches any string
            matchAndAddTokens(fallbackPattern, content, tokens);
        }

        return tokens;
    }

    private void matchAndAddTokens(Pattern pattern, String content, List<String> tokens) {
        Matcher matcher = pattern.matcher(content);
        while (matcher.find()) {
            tokens.add(matcher.group());
        }
    }
}
