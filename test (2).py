import time
from tkinter import *
from tkinter import messagebox

# creating Tk window
root = Tk()

# setting geometry of tk window
root.geometry("300x250")

# Using title() to display a message in
# the dialogue box of the message in the
# title bar.
root.title("Countdown timer")

# Declaration of variables
hour = StringVar()
minute = StringVar()
second = StringVar()

# setting the default value as 0
hour.set("00")
minute.set("00")
second.set("00")

# Use of Entry class to take input from the user
hourEntry = Entry(root, width=3, font=("Arial", 18, ""),
                  textvariable=hour)
hourEntry.place(x=80, y=20)

minuteEntry = Entry(root, width=3, font=("Arial", 18, ""),
                    textvariable=minute)
minuteEntry.place(x=130, y=20)

secondEntry = Entry(root, width=3, font=("Arial", 18, ""),
                    textvariable=second)
secondEntry.place(x=180, y=20)

def submit():
    global temp
    global stop_var
    global reset_var
    stop_var =False
    reset_var=False
    try:
        # the input provided by the user is
        # stored in here :temp
        temp = int(hour.get()) * 3600 + int(minute.get()) * 60 + int(second.get())
    except:
        print("Please input the right value")
    while temp > -1:
        if(stop_var or reset_var):
            break
        # divmod(firstvalue = temp//60, secondvalue = temp%60)
        mins, secs = divmod(temp, 60)

        # Converting the input entered in mins or secs to hours,
        # mins ,secs(input = 110 min --> 120*60 = 6600 => 1hr :
        # 50min: 0sec)
        hours = 0
        if mins > 60:
            # divmod(firstvalue = temp//60, secondvalue
            # = temp%60)
            hours, mins = divmod(mins, 60)

        # using format () method to store the value up to
        # two decimal places
        hour.set("{0:2d}".format(hours))
        minute.set("{0:2d}".format(mins))
        second.set("{0:2d}".format(secs))

        # updating the GUI window after decrementing the
        # temp value every time
        root.update()
        time.sleep(1)

        # when temp value = 0; then a messagebox pop's up
        # with a message:"Time's up"
        if (temp == 0):
            messagebox.showinfo(" Countdown timer", "Time's up ")

        # after every one sec the value of temp will be decremented
        # by one
        temp -= 1
def stopfunc():
    global stop_var
    stop_var=True
def resetfunc():
    global reset_var
    reset_var =True
    messagebox.showinfo(" Reset", "Countdown reset ")
    hour.set("00")
    minute.set("00")
    second.set("00")

    


# button widget
btn = Button(root, text='Start', bd='5',
             command=submit)
             
btn.place(x=70, y=120)
stop_btn = Button(root, text='Stop', bd='5',command=stopfunc)
stop_btn.place(x=120,y=120)
reset_btn = Button(root, text='Reset', bd='5',command=resetfunc)
reset_btn.place(x=170,y=120)

# infinite loop which is required to
# run tkinter program infinitely
# until an interrupt occurs
root.mainloop()



# def func(potential,k):
#     wintpot=int()
#     for i in range(k):
#         one=potential[0]
#         two=potential[1]
#         if(one<two):
#             winpot=potential[1]
#             temp=potential.pop(0)
#         else:
#             winpot=potential[0]
#             temp=potential.pop(1)
#         potential.append(temp)
#     return winpot


# print(func([1,3,2,4,5],2))