package com.example.www.pages.restful_booker;//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by FernFlower decompiler)
//


import com.google.common.primitives.Primitives;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.openqa.selenium.Alert;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.WebDriver.Navigation;
import org.openqa.selenium.WebDriver.Options;
import org.openqa.selenium.WebDriver.Timeouts;
import org.openqa.selenium.WebDriver.Window;
import org.openqa.selenium.support.decorators.Decorated;
import org.openqa.selenium.support.decorators.WebDriverDecorator;
import org.openqa.selenium.support.events.WebDriverListener;


public class CustomEventFiringDecorator extends WebDriverDecorator {
    private static final Logger logger = Logger.getLogger(CustomEventFiringDecorator.class.getName());
    private final List<WebDriverListener> listeners;

    public CustomEventFiringDecorator(WebDriverListener... listeners) {
        this.listeners = Arrays.asList(listeners);
    }

    public void beforeCall(Decorated<?> target, Method method, Object[] args) {
        this.listeners.forEach((listener) -> {
            this.fireBeforeEvents(listener, target, method, args);
        });
        super.beforeCall(target, method, args);
    }

    public void afterCall(Decorated<?> target, Method method, Object[] args, Object result) {
        super.afterCall(target, method, args, result);
        this.listeners.forEach((listener) -> {
            this.fireAfterEvents(listener, target, method, result, args);
        });
    }

    public Object onError(Decorated<?> target, Method method, Object[] args, InvocationTargetException e) throws Throwable {
        this.listeners.forEach((listener) -> {
            try {
                listener.onError(target.getOriginal(), method, args, e);
            } catch (Throwable var6) {
                //logger.log(Level.WARNING, var6.getMessage(), var6);
                logger.log(Level.INFO, "test");
            }

        });
        return super.onError(target, method, args, e);
    }

    private void fireBeforeEvents(WebDriverListener listener, Decorated<?> target, Method method, Object[] args) {
        try {
            listener.beforeAnyCall(target.getOriginal(), method, args);
        } catch (Throwable var10) {
            logger.log(Level.WARNING, var10.getMessage(), var10);
        }

        try {
            if (target.getOriginal() instanceof WebDriver) {
                listener.beforeAnyWebDriverCall((WebDriver)target.getOriginal(), method, args);
            } else if (target.getOriginal() instanceof WebElement) {
                listener.beforeAnyWebElementCall((WebElement)target.getOriginal(), method, args);
            } else if (target.getOriginal() instanceof Navigation) {
                listener.beforeAnyNavigationCall((Navigation)target.getOriginal(), method, args);
            } else if (target.getOriginal() instanceof Alert) {
                listener.beforeAnyAlertCall((Alert)target.getOriginal(), method, args);
            } else if (target.getOriginal() instanceof Options) {
                listener.beforeAnyOptionsCall((Options)target.getOriginal(), method, args);
            } else if (target.getOriginal() instanceof Timeouts) {
                listener.beforeAnyTimeoutsCall((Timeouts)target.getOriginal(), method, args);
            } else if (target.getOriginal() instanceof Window) {
                listener.beforeAnyWindowCall((Window)target.getOriginal(), method, args);
            }
        } catch (Throwable var9) {
            logger.log(Level.WARNING, var9.getMessage(), var9);
        }

        String methodName = this.createEventMethodName("before", method.getName());
        int argsLength = args != null ? args.length : 0;
        Object[] args2 = new Object[argsLength + 1];
        args2[0] = target.getOriginal();

        for(int i = 0; i < argsLength; ++i) {
            args2[i + 1] = args[i];
        }

        Method m = this.findMatchingMethod(listener, methodName, args2);
        if (m != null) {
            this.callListenerMethod(m, listener, args2);
        }

    }

    private void fireAfterEvents(WebDriverListener listener, Decorated<?> target, Method method, Object res, Object[] args) {
        String methodName = this.createEventMethodName("after", method.getName());
        boolean isVoid = method.getReturnType() == Void.TYPE || method.getReturnType() == Timeouts.class;
        int argsLength = args != null ? args.length : 0;
        Object[] args2 = new Object[argsLength + 1 + (isVoid ? 0 : 1)];
        args2[0] = target.getOriginal();

        for(int i = 0; i < argsLength; ++i) {
            args2[i + 1] = args[i];
        }

        if (!isVoid) {
            args2[args2.length - 1] = res;
        }

        Method m = this.findMatchingMethod(listener, methodName, args2);
        if (m != null) {
            this.callListenerMethod(m, listener, args2);
        }

        try {
            if (target.getOriginal() instanceof WebDriver) {
                listener.afterAnyWebDriverCall((WebDriver)target.getOriginal(), method, args, res);
            } else if (target.getOriginal() instanceof WebElement) {
                listener.afterAnyWebElementCall((WebElement)target.getOriginal(), method, args, res);
            } else if (target.getOriginal() instanceof Navigation) {
                listener.afterAnyNavigationCall((Navigation)target.getOriginal(), method, args, res);
            } else if (target.getOriginal() instanceof Alert) {
                listener.afterAnyAlertCall((Alert)target.getOriginal(), method, args, res);
            } else if (target.getOriginal() instanceof Options) {
                listener.afterAnyOptionsCall((Options)target.getOriginal(), method, args, res);
            } else if (target.getOriginal() instanceof Timeouts) {
                listener.afterAnyTimeoutsCall((Timeouts)target.getOriginal(), method, args, res);
            } else if (target.getOriginal() instanceof Window) {
                listener.afterAnyWindowCall((Window)target.getOriginal(), method, args, res);
            }
        } catch (Throwable var13) {
            logger.log(Level.WARNING, var13.getMessage(), var13);
        }

        try {
            listener.afterAnyCall(target.getOriginal(), method, args, res);
        } catch (Throwable var12) {
            logger.log(Level.WARNING, var12.getMessage(), var12);
        }

    }

    private String createEventMethodName(String prefix, String originalMethodName) {
        return prefix + originalMethodName.substring(0, 1).toUpperCase() + originalMethodName.substring(1);
    }

    private Method findMatchingMethod(WebDriverListener listener, String methodName, Object[] args) {
        Method[] var4 = listener.getClass().getMethods();
        int var5 = var4.length;

        for(int var6 = 0; var6 < var5; ++var6) {
            Method m = var4[var6];
            if (m.getName().equals(methodName) && this.parametersMatch(m, args)) {
                return m;
            }
        }

        return null;
    }

    private boolean parametersMatch(Method m, Object[] args) {
        Class<?>[] params = m.getParameterTypes();
        if (params.length != args.length) {
            return false;
        } else {
            for(int i = 0; i < params.length; ++i) {
                if (args[i] != null && !Primitives.wrap(params[i]).isAssignableFrom(args[i].getClass())) {
                    return false;
                }
            }

            return true;
        }
    }

    private void callListenerMethod(Method m, WebDriverListener listener, Object[] args) {
        try {
            m.invoke(listener, args);
        } catch (Throwable var5) {
            logger.log(Level.WARNING, var5.getMessage(), var5);
        }

    }
}
