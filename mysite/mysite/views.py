from django.shortcuts import render,redirect
from django.http import HttpResponse

def home(request):
    if request.method=='POST':
        if 'save' in request.POST:
           print("save")
        elif 'finish' in request.POST:
           print("finish")
    return render(request,"home.html")
def home2(request):
    val1=request.POST["a1"]
    val2=request.POST["a2"]
    res=int(val1)+int(val2)
    return render(request,'home2.html',{'result':res})