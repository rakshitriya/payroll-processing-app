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

