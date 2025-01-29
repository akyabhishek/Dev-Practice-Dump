from django.db import models

from . import settings

class User(models.Model):
    name = models.CharField(max_length=255)
    dob = models.DateField()
    email = models.EmailField()
    phone = models.CharField(max_length=20)