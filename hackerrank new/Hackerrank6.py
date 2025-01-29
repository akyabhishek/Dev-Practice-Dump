def timeConversion(s):
    tft=s.split(":")
    q=tft[2]
    q=q.upper()
    tft[2]=q[:2]
    if(q[2]=="A"):
        if(int(tft[0])==12):
            tft[0]='00'
    elif (q[2] == "P"):
        if(int(tft[0])<12):
            tft[0]=str(int(tft[0])+12)
    nt=':'
    nt=nt.join(tft)
    return nt

s="01:40:22pm"
q=timeConversion(s)
print(q)