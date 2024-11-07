To run the program from the main root folder,:
  -  type `gradle build`.
  -  type `gradle run`.

I have already added a sample input file inside the `src` folder.

If you need to change the input file you can replace `employee_details.txt` file in the `build.gradle` file as:
```
run {
    args 'employee_details.txt' 
}
```

A sample output is:

<img width="1328" alt="output" src="https://github.com/user-attachments/assets/b0a9d6f3-4031-407a-8a41-53a5e0ffef31">
