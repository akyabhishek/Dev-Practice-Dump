from django.shortcuts import render, redirect
from .models import User

def user_form(request):
    if request.method == 'POST':
        name = request.POST['name']
        dob = request.POST['dob']
        email = request.POST['email']
        phone = request.POST['phone']
        user = User(name=name, dob=dob, email=email, phone=phone)
        user.save()
        return redirect('success')
    else:
        return render(request, 'user_form.html')
    
def success(request):
    return render(request, 'success.html')