import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class AdvancedTokenizer implements Tokenizer {

    @Override
    public List<String> tokenize(String content) {
        List<String> tokens = new ArrayList<>();

        // Step 1: Simple regex - matches words, numbers, and basic symbols
        Pattern simplePattern = Pattern.compile("[\\w]+|[!?.;,:()\"'#$%&*]+");
        matchAndAddTokens(simplePattern, content, tokens);

        // Step 2: Intermediate regex - handles words combined with symbols (e.g., Ceva!wor)
        Pattern intermediatePattern = Pattern.compile("[\\w'-]+(?:[!?.;,:()\"'#$%&*]+[\\w'-]*)*|[!?.;,:()\"'#$%&*]+");
        matchAndAddTokens(intermediatePattern, content, tokens);

        // Step 3: Advanced regex - handles even more complex patterns with mixed tokens
        Pattern advancedPattern = Pattern.compile("\\w+|[^\\w\\s]+");
        matchAndAddTokens(advancedPattern, content, tokens);

        Pattern symbolsFollowedByWordPattern = Pattern.compile("[#@%$&]+[\\w'-]+");
        matchAndAddTokens(symbolsFollowedByWordPattern, content, tokens);

        Pattern wordWithSymbolsPattern = Pattern.compile("[\\w'-]+[!?.;,:()\"'#$%&*]+[\\w'-]+");
        matchAndAddTokens(wordWithSymbolsPattern, content, tokens);

        Pattern pattern = Pattern.compile(".*");  // Matches any string
        matchAndAddTokens(pattern, content, tokens);

        return tokens;
    }

    private void matchAndAddTokens(Pattern pattern, String content, List<String> tokens) {
        Matcher matcher = pattern.matcher(content);
        while (matcher.find()) {
            tokens.add(matcher.group());
        }
    }
}
