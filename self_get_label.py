# -*- coding: utf-8 -*-

import requests
import getpass
import os
import threading
from threading import Event,Thread
from  flask import Flask, jsonify
app = Flask(__name__)

base_url = 'http://39.108.69.214:8080/xxzx/a/'
headers = {'user-agent': 'Mozilla/5.0 (Linux; Android 5.1.1; Nexus 6 Build/LYZ28E)'
                         ' AppleWebKit/537.36 (KHTML, like Gecko) '
                         'Chrome/58.0.3029.110 Mobile Safari/537.'}
cookies = 'cookies'
floder_url = ''
all_md5 = []
all_label_out = ""

def main():
    username = input('请输入登录名：')
    while username == '':
        print('登录名为空')
        username = input('请输入登录名：')

    # password = input('请输入密码：')
    password = getpass.win_getpass('请输入密码：')
    while password == '':
        print('密码为空')
        # password = input('请输入密码：')
    # 使用getpass模块,输入密码时，不可见
        password = getpass.win_getpass('请输入密码：')

    # if(os = unix):
    #     password = getpass.unix_getpass('请输入密码：')
    login(username, password, headers)
    get_all_label()




#登录，获取用户信息和cookie
def login(username,password,headers):
    global cookies
    login_url = base_url + 'login?__ajax=true'
    account = {'username' : username , 'password' : password, 'mobileLogin' :'true'}
    login_info = requests.post(login_url, data=account, headers = headers)
    if len(login_info.text) > 14000:
        print('网络连接不可用')
        os.system("pause")
    else:
        print('登录信息：')
        print(login_info.json())
    login_info_json = login_info.json()
    print(login_info_json['name'] + '登录成功')
    cookies = login_info_json['sessionid']


#获取所有标签和图片地址
def get_all_label():
        payload = {'keyWord':'*'}
        global all_label_out
        all_label = requests.get(base_url+"tpsb/queryPicByKeyWord;JSESSIONID="+cookies,params=payload)
        all_label_out = all_label.json()
        print(all_label_out)


#转发到web
@app.route('/')
def output_web():
    return jsonify(all_label_out)


if __name__ == '__main__':
    main()
    app.run()