from django.db import models

# Create your models here.
class name(models.Model):
    aa=models.CharField(max_length=50)
    def __str__(self):
        return self.aa