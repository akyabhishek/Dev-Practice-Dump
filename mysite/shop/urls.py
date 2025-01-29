from django.contrib import admin
from django.urls import path
from . import views

urlpatterns = [
    path("",views.index,name='ShopHome'),
    path("about/",views.about,name='About'),
    path("contact/",views.contact,name='ContactUs'),
    path("tracker/",views.tracker,name='Track'),
    path("Search/",views.search,name='Search'),
    path("productview/",views.prodview,name='Product'),
    path("checkout/",views.checkout,name='Checkout')
]
