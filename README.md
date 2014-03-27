textgenerator
=============

CS2020 2014 PS6

This java project is actually a school project of mine. It generates a string of English sentences given a text file.

Place a .txt file in the same level as the package folder as the base text for the TextGenerator to generate a MarkovModel, and supply the order of the Markov Model as well as the length of string you want to generate. An order of 3 to 5 is recommended to generate a (somewhat) sensible English sentences.

'Order' of Markov Model indicates the number of characters the text generator is basing on when making predictions. For example, an order of 3 means that it is making prediction for a next character based on the previous 3 characters. To illustrate, let's say the 3 previous chars are 'abd', then it can predict the next char for that string based on the text file that you supplied. (Therefore, the longer the better). The result would be something similar to the text you provided, or at least sounds like it is from the same person.

First working version! Feel free to play around with it! Try out different texts for a wide range of results! =D