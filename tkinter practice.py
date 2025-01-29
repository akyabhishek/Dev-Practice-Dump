from tkinter import *

# root= Tk()
# root.geometry("500x500")
# root.title("Abk")
# btn=Button(root,text="Hello")
# btn2=Button(root,text="Hello wow",bg="red")
# # btn.pack(side="right")
# # btn2.place(x=400,y=400)
# # btn.grid(row=0,column=0)
# # btn2.grid(row=0,column=1)
# root.mainloop()


# def handler1():  #Event Handler
#     print("Button is Clicked")
# root=Tk()
# root.geometry("500x500")
# btn=Button(root,text="Submit",font=True,command=handler1)
# btn.grid(row=0,column=0)
# root.mainloop()

# def btn1_click():
#     print("Submit Button is Pressed")
# def btn2_click():
#     print("Cancel Button is Pressed")
# root=Tk()
# root.geometry("300x300")
# btn1=Button(root,text="Submit",font=True,command=btn1_click)
# btn1.grid(row=0,column=0)
# btn2=Button(root,text="Cancel",font=True,command=btn2_click)
# btn2.grid(row=0,column=1)
# root.mainloop()

def btn_click():
    s=var.get()
    print(s)
root=Tk()
root.geometry("400x400")
lbl=Label(root,text="Enter your name",font=True)
lbl.grid(row=0,column=0)
var=StringVar()
etr1=Entry(root,textvariable=var,font=True)
etr1.grid(row=1,column=0)
btn=Button(root,text="submit",font=True,command=btn_click)
btn.grid(row=2,column=0)
root.mainloop()