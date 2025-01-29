from django.contrib import admin
from django.urls import path,include
from . import views
from django.conf import settings
from django.conf.urls.static import static

urlpatterns = [
    path('admin/', admin.site.urls),
    path('',views.home,name='home'),
    path('home2',views.home2,name='home2'),
    path('shop/', include('shop.urls')),
    path('blog/', include('blog.urls')),
    path('test/', include('testapp.urls'))
] + static(settings.MEDIA_URL,document_root=settings.MEDIA_ROOT)
 