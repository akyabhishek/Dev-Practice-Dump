from django.shortcuts import render
from .forms import StudentRegistration
from django.http import JsonResponse
from .models import User
# Create your views here.
def index(request):

    form=StudentRegistration()
    user=User.objects.all()
    return render(request,"testapp/tindex.html",{'form':form,'users':user})

def save_data(request):
    if request.method == "POST":
        form=StudentRegistration(request.POST)
        if form.is_valid():
            name=request.POST['name']
            email=request.POST['email']
            password=request.POST['password']
            fr=User(name=name,email=email,password=password)
            fr.save()
            return JsonResponse({'status':'save'})
        else:
            return JsonResponse({'status':0})
def multi(request):

    return render(request,"testapp/multi.html",)