# -*- coding: utf-8 -*-
from ctypes import *

# Загружаем библиотеку для математический функций
libc = CDLL("../gmath/lib/gmathlib.so")


# Создаём класс для работы со структурой из библиотеки
class FunctionValue(Structure):
    _fields_ = [("argument", c_double), ("value", c_double)]


arg = c_double(-2.0)
libc.function.restype = FunctionValue
print(libc.function(arg).value)
i = -2.0
while i <= 2.0:
    i += 0.25
    result = libc.function(c_double(i)).value
    print(result)
