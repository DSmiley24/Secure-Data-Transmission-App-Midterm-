# Secure-Data-Transmission-App-Midterm-

This code that I've written demonstrates the big triad of secure coding: Confidentiality, Integrity, and Availability (CIA). The Confidentiality aspect is uphelt through use of AES encryption that converts
the user's message into scrambled nonsense until you "unlock" it with the same key used to "lock" it. The Integrity part is kept because I compared the hash of the original message to the decrypted one, ensuring
that data was not altered during the encryption process. Lastly, the Availability is kept because it's a super simple app to use. You type in your message, the program encrypts and decrypts the message for you, 
and asks if you'd like to continue or leave. Voila!

As for the entropy, I generated random keys by using Java's KeyGenerator class. This ensures that the keys are difficult (nigh, impossible!) to uncover. This makes brute forcing much less likely and makes the
program more secure. Without proper entropy the keys can become predictable and easy to nab, which has unfortunately been the case in lots of security breaches in the past decade.
