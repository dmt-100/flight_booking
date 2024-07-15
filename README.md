Описание схемы
Основной сущностью является бронирование (bookings).

В одно бронирование можно включить несколько пассажиров, каждому из которых выписывается отдельный билет (tickets). Билет имеет уникальный номер и содержит информацию о пассажире. Как таковой пассажир не является отдельной сущностью. Как имя, так и номер документа пассажира могут меняться с течением времени, так что невозможно однозначно найти все билеты одного человека; для простоты можно считать, что все пассажиры уникальны.

Билет включает один или несколько перелетов (ticket_flights). Несколько перелетов могут включаться в билет в случаях, когда нет прямого рейса, соединяющего пункты отправления и назначения (полет с пересадками), либо когда билет взят «туда и обратно». В схеме данных нет жёсткого ограничения, но предполагается, что все билеты в одном бронировании имеют одинаковый набор перелетов.

Каждый рейс (flights) следует из одного аэропорта (airports) в другой. Рейсы с одним номером имеют одинаковые пункты вылета и назначения, но будут отличаться датой отправления.

При регистрации на рейс пассажиру выдаётся посадочный талон (boarding_passes), в котором указано место в самолете. Пассажир может зарегистрироваться только на тот рейс, который есть у него в билете. Комбинация рейса и места в самолете должна быть уникальной, чтобы не допустить выдачу двух посадочных талонов на одно место.

Количество мест (seats) в самолете и их распределение по классам обслуживания зависит от модели самолета (aircrafts), выполняющего рейс. Предполагается, что каждая модель самолета имеет только одну компоновку салона. Схема данных не контролирует, что места в посадочных талонах соответствуют имеющимся в самолете (такая проверка может быть сделана с использованием табличных триггеров или в приложении).


==================================================================================

Description of the scheme The main entity is bookings.

Several passengers can be included in one booking, each of whom is issued a separate ticket (tickets). The ticket has a unique number and contains information about the passenger. As such, the passenger is not a separate entity. Both a passenger's name and document number may change over time, so it is not possible to uniquely locate all of one person's tickets; For simplicity, we can assume that all passengers are unique.

A ticket includes one or more flights (ticket_flights). Multiple flights may be included in a ticket in cases where there is no direct flight connecting the points of origin and destination (flight with transfers), or when the ticket is taken “round-trip”. There is no hard constraint in the data schema, but it is assumed that all tickets in the same booking have the same set of flights.

Each flight goes from one airport to another. Flights with the same number have the same origin and destination, but will differ in departure date.

When checking in for a flight, the passenger is issued a boarding pass (boarding_passes), which indicates his seat on the plane. A passenger can only check in for the flight that is on his ticket. The flight and seat combination must be unique to prevent two boarding passes being issued for one seat.

The number of seats on the plane and their distribution by class of service depends on the model of aircraft operating the flight. It is assumed that each aircraft model has only one cabin layout. The data schema does not check that the seats on the boarding passes match those available on the plane (this check can be done using table triggers or in the application).
![](src/main/resources/Data_schema_diagram.jpg)