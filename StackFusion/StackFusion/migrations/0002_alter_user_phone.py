# Generated by Django 3.2.18 on 2023-03-13 05:32

from django.db import migrations, models


class Migration(migrations.Migration):

    dependencies = [
        ('StackFusion', '0001_initial'),
    ]

    operations = [
        migrations.AlterField(
            model_name='user',
            name='phone',
            field=models.CharField(max_length=20),
        ),
    ]
