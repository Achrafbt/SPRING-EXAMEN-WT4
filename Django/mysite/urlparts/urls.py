from django.conf.urls import url

from . import views

urlpatterns = [
    url(r'^(?P<param1>[A-z 0-9&.-]+)/(?P<param2>[A-z 0-9&.-]+)/(?P<param3>[A-z 0-9&.-]+)$', views.index, name='index'),
]
