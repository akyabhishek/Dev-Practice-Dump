from django.shortcuts import render, redirect
from django.http import HttpResponse

from .models import name

def index(request):
    if (request.POST):
        abc=request.POST["abc"]
        x=name(aa=abc)
        x.save()
        print("Data inserted")
        return redirect('/blog')
    else:
        return render(request,"blog/index.html")
def blog(request):
    names = name.objects.all
    return render(request,"blog/blog.html",{'names':names})