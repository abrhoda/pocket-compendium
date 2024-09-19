1. Dice class has a roll method. This is a class because its stateful.
2. `private static final Parser`:
   1. take string and split into proper tokens.
   2. validate each token (are dice expressions valid)
   3. convert to postfix notation (this is our `AST`)
3. `public class Dice`:
   1. has `private static final Parser` field
   2. has a roll method, which is public and returns an int
   3. roll method takes raw input string, does some minor validations, and then sanitizes (remove whitespace)
   4. calls the parser's tokenize/parse method and generates "tokens" for each statement.
   5. parser builds AST from these tokens (in this case it'll just be a postfix notation queue with the tokens)
   6. dice class's evaluate method takes this AST and outputs an int which is returns to the caller.


```
from here: https://github.com/airjp73/dice-notation/tree/master

// Gets the tokens from the lexer
// Example: [DiceRollToken, OperatorToken, DiceRollToken]
const tokens = tokenize('2d6 + 3d4');

// Rolls any dice roll tokens and returns all the individual rolls.
// rolls[i] contains all the rolls for the DiceRollToken at tokens[i]
// Example: [[3, 1], null, [1, 3, 2]]
const rolls = rollDice(tokens);

// Takes the rolls and totals them
// Example: [4, null, 6]
const rollTotals = tallyRolls(tokens, rolls);

// Get the final result of the roll
// Example: 10
const result = calculateFinalResult(tokens, rollTotals);

```


Some other dice expressions that should be supported are:
- XdY<khZ> == XdY<kZ>
- XdY<klZ>
- XdY<dhZ>
- XdY<dlZ> == XdY<dZ>
- XdY adv/dis for advantage and disadvantage (shortcut for 2d20k1 and 2d20d1)