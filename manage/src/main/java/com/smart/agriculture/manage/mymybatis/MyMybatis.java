package com.smart.agriculture.manage.mymybatis;

import com.smart.agriculture.manage.entity.MachineControllerEntity;
import org.springframework.cglib.proxy.InvocationHandler;
import org.springframework.cglib.proxy.Proxy;

import java.lang.reflect.Method;
import java.util.List;

public class MyMybatis {

    public static void main(String[] args) {
        EmpMapper o = (EmpMapper)Proxy.newProxyInstance(MyMybatis.class.getClassLoader(), new Class[]{EmpMapper.class}, new InvocationHandler() {
            @Override
            public Object invoke(Object o, Method method, Object[] objects) throws Throwable {
                System.out.println(method.getName());
                return null;
            }
        });


        List<MachineControllerEntity> mark = o.selectMachineController("mark", "1");
    }
}
