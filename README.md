# About Yahtzee game
- [What is Yahtzee game?](https://en.wikipedia.org/wiki/Yahtzee)
- [Online Yahtzee game example](https://cardgames.io/yahtzee/)
  ![image](https://user-images.githubusercontent.com/49010295/234011002-88c47143-994d-45fe-854b-2fa44593fe72.png)

## UML
### Sequence Diagram
<img src="https://user-images.githubusercontent.com/49010295/235510664-19b848e7-aab3-4916-84be-e14177c9a83e.png" width="600">

![image](https://user-images.githubusercontent.com/49010295/235519657-430ecb6b-0da9-4145-aec9-6aa9616c5773.png)

## Test Coverage
<img src="https://user-images.githubusercontent.com/49010295/234103472-7d9bbb14-f066-47e3-b97d-e822f7e3891e.png" width="500">

## Rule

- Player rolls 5 dice
  - They will have three throwing opportunities (if they don't like the current combo, rethrow).
  - Players can rethrow only certain dice.
  - Behaviour Example
    - Throws 5 dice -> Keep 2 dice, throws 3 dice again -> Keep 4 and throws 1 die again.

- Player choose one of categories that are available by the dice combination
  - Categories (13 types)
    - Upper section
      ![image](https://user-images.githubusercontent.com/49010295/234079367-a2032d22-9937-45a9-a9b8-1b7fa901bd38.png)
    - Lower section ![image](https://user-images.githubusercontent.com/49010295/234079327-37ee4db2-129c-4dfd-be20-06776c6c2c0f.png)
- When all categories are filled, the scores of the categories are added together, and the one with the highest score is the winner.






























