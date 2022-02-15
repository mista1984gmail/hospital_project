Веб-проект Система Больница.
Задание: "Система Больница. Врач определяет диагноз, делает назначение Пациенту (процедуры, лекарства, операции).
Назначение может выполнить Медсестра (процедуры, лекарства) или Врач (любое назначение).
Пациент может быть выписан из Больницы, при этом фиксируется окончательный диагноз."

Стек-технологий, который я использовал при написании проекта:
- в качестве базы данных, использовался MySQL;
- в качестве фреймворка для сборки проекта - Maven;
- сам проект написан с использованием Spring Boot;
- пользовательский интерфейс был написан с использованием bootstrap, также использовался CSS, в качестве шаблонизатора - thymeleaf, использовался также немного js.
- среда разработки - intellij idea;
- репозиторий проетка размещался на github.  

Схема таблиц в базе данных представлена ниже на рисунке:  


<https://drive.google.com/file/d/1Au0Ipz-bumCnh5v_6PEB5kRX1ig-7JcV/view?usp=sharing>

Мой проект использует архитектуру mvc. Имеются три основные слои приложения: модели, представления и контроллеры.  


**Возможности приложения.**  

Имеются четыре основных роли в моем приложении: admin (администратор), user (пациент), doctor (доктор), nurse (медсестра).
Пользователь заходит на сайт и может зарегистрироваться указав корректные данные, если данные некоторые не корректны и 
не проходят валидацию, то будет выведена соответствующая подсказка, что необходимо поправить.
Далее пользователю на указанный при регистрации е-майл приходит письмо с подтверждением данного почтового ящика.
Пользователь переходит по ссылке в письме и может уже авторизоваться в приложении. 
При входе в приложение - приложение смотрит какая роль у того кто входит и выдает ему соответствующую страницу. 
Разные пользователи не могут видеть информацию, которая им не подходит по роли.  

**ADMIN** 

Администратор может видеть всех пользователей у себя на странице, может осуществлять поиск по какому-то пользователю, вводя буквы имени или фамилии (реализовано через js-скрипт).
Также администратор может редактировать данные по пользователю, менять его роль, а также далать его активным или наоборот заблокировать пользователя и тогда данный пользователь не сможет войти под своим именем и паролем.  

**USER**

Пациент войдя на свою страницу может видеть информацию о себе в своем кабинете, может ее изменять. 
Также видит всю информацию о истории болезни, анализах, назначенных медикаментах, процедурах и кто (какой доктор сделал это назначение).  

**DOCTOR** 

Доктор зайдя на свою страницу видит перечень всех Пациентов, может найти какого-нибуде пациента вводя в строку поиска имя или фамилию пациента.
Также доктор может войти в историю болезни Пациента, гда он видит краткую инфорцацию о Пациенте, а также может сделать новое назначение из перечня предложенных услуг. 
То есть назначить какой-то анализ, процедуру, прописать медикаменты или назначить операцию. 
Также доктор может сделать выполнение назначения поставив соответствующую галочку об выполнении и произойдет запись в базу данных о том кто сделал это выполнение.  

**NURSE**
Медсестра войдя в систему может просматривать всех пациентов и искать пациента. Также она может добавлять новые категории и конкреные виды услуг со стоимостью.
Медсестра может сделать выполнение по Пациенту тех назначений, которые сделал Врач, кроме Операций. Когда медсестра вносит результаты анализов по Пациенту они автоматически отправляются на почту Пациенту.
Также медсестра может в искать назначения по дате, введя дату на которую необходимо найти назначенные поциенту услуги. В конце лечения медсестра может сформировать счет на оплату услуг, его сохранить и выслать Пациенту нажатием одной кнопки на почту.
