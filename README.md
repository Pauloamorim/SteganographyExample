# SteganographyExample
> Steganography is a technique of inserting information in a media, which can be image files, sound files or video files.This technique aims to send information between the sender and the recipient unnoticed by others.

This code target to create an implementation of Secure Steganography on Jpeg Image Using LSB Method

Reference Article used to build this: https://www.ripublication.com/ijaer18/ijaerv13n1_60.pdf

## Results
This is the original image, downloaded from Google
![horizon](https://user-images.githubusercontent.com/7330132/180094148-fe0d28f1-a1d7-4928-a029-d643592236ff.jpeg)
This is the new image with an another image hidden inside!
> Believe it or not we have an Mario image inside that. you can just save that image and run the decode operation to see it.
![_horizon](https://user-images.githubusercontent.com/7330132/180094445-2c8a3fe4-1b99-4946-bc6b-d234994ebffa.jpeg)
- Mario image that it's hidden
- ![decoded](https://user-images.githubusercontent.com/7330132/180094678-c9e2eaa2-49d9-4b76-94eb-4f366cce9058.jpeg)

## Tested types of file that are working
- [x] pdf
- [x] txt
- [x] mp3
- [x] jpeg


## Lessons Learned
- Lots of applications like Facebook, Whatsapp and Twitter make it impossible to use this technique, since everytime we send an image using their plataform they resize and change quality of the image, which will completlty destroy the hidden message 

## TODO List
- [ ] Improve this README.md
- [ ] Improve and Finish unit tests (I'm kinda lazy to finish that :( )
- [ ] Create an Interface to use this code
