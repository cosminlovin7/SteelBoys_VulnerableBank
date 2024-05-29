# SteelBoys_VulnerableBank
This is a CTF Android application for a project.

Students:
Dragos Petre, SCPD
Cosmin-Viorel Lovin, SCPD

GitHub link for the Android CTF application: https://github.com/cosminlovin7/SteelBoys_VulnerableBank
GitHub link for the Backend server: https://github.com/cosminlovin7/SteelBoys_VulnerableBank_Server

IMPORTANT: In order to run the application correctly, the backend server must be running.

CHALLENGE 1:
Task: Inspect the application... Is there anything wrong? Any forgotten logs? What strange things do you observe?
Hint: Use static analysis for this challenge. Inspect any possible vulnerable information logged. What happens when a client successfully logs in?
Answer: There are plenty of problems when inspecting the MainActivity file. First of all, the username and password are logged in clear format. Secondly, after the login is successfull, the username and password used for connecting are stored in a local database in clear format. An attacker could reach this file and identify possible combinations of usernames and passwords. People tend to use the same username and password on multiple platforms. This could lead to bigger damage than just access to this application.

CHALLENGE 2:
Task: Bypass root detection
Hint: Use dynamic analysis, Frida for example. Change the behavior of the OK button. Prevent the OK button from closing the application.

CHALLENGE 1:
Task: Try to identify the correct username and password
Hint: Use a tool like Burp to analyze the network traffic. Try to identify the URL we try to send the credentials to.
Answer: Using a tool to analyze the network traffic will help us understand what is the URL we should follow. If we continue the request and analyze the result we receive, we will notice that for each combination of wrong username and password we receive a message that actually tells us what the real password should be. Note that this password will work with any possible username.
FLAG: CTF_SMD_LOGIN_FLAG_2024

CHALLENGE 4:
Task: Identify the hidden flag in Register button
Hint: Use static analysis. Follow the hints provided in the code.

CHALLENGE 5:
Task: Try to use the transfer button. Send someone a small attention :)
Hint: Use static analysis to determine the way the IBAN format should be. No other IBAN's are accepted.
Answer: We can see in the code that the requested IBAN is compare to a regex... We have multiple options to choose from. Any of them is the correct one.
FLAG: IBAN is any string that matches this regex: /^CTF(RNCB|ING0|BT00|SDM0)[0-9]{4}PINFLAG2024$/

CHALLENGE 6:
Task: Try to decode the PIN value.
Hint: Use dynamic analysis to change the way PIN validation is done.