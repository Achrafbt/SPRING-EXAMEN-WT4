# -*- coding: utf-8 -*-
from __future__ import unicode_literals

from django.shortcuts import render
from django.template import RequestContext

import urllib, urllib2

# Create your views here.
def index(request, param1, param2, param3):
    param1 = request.GET('param1')
    param2 = request.GET('param2')
    param3 = request.GET('param3')

    context = {
        'param1': param1,
        'param2': param2,
        'param3': param3,
    }
    return render(request, 'urlparts/index.html', context)