# Steganography Example using Least Significant BIT (Currently in development)
Code to generate a image with a hidden message.
If you're not familiar with Steganography, please take a look at these two links:
- https://en.wikipedia.org/wiki/Steganography
- https://www.youtube.com/watch?v=TWEXCYQKyDc

## How to Run
arguments:
 - 0 -> Path to image
 - 1 -> Text to be hidden
 - 2 -> Path to output image

## Actual status
The first image is the original and the second one hold 50 paragraphs of Lorem Ipsum
![jpegsystems-home](https://user-images.githubusercontent.com/7330132/43341544-3ad95adc-91b6-11e8-929d-cdc1432ae4ac.jpg)

### Updated image:
![newfile](https://user-images.githubusercontent.com/7330132/43341574-518ffc4a-91b6-11e8-8857-ac65630086f0.jpg)

See the difference? no, I know hahaha
Steganography ROCKS

### TODO LIST:
 - Pass image and message as a parameter to program. Now they are hard-coded. (DONE)
 - Always convert the image to format .bmp (DONE)
 - Better documentation in code.
 - Improve code(<- Really need this, since at first time I just focus and let the things working) and performance 
 - Encrypt message
 - Create unit tests
 - Provide chance to pass a file to be hide in image
