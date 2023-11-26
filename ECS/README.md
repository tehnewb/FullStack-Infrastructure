<div style="text-align: center;"><b><h1>Entity Component System Module</h1></b>

*This module contains an Entity-Component-System (ECS) framework, offering a modular architecture for efficient
development. The project aims to provide a flexible and extensible foundation for building
scalable and performance-oriented systems.*

</div>

<div style="text-align: center;"><h3>Implementations</h3></div>

+ ECS
    + This is the main class for managing entities, their components, and systems that process them. This must be
      constructed with the systems you plan on using.
+ EntitySystem
    + abstract base class for systems in an Entity-Component-System (ECS) architecture. It manages entities, their
      components, and provides a framework for processing entities based on specific components.
+ Entity
    + Entities are fundamental objects that can have various components attached to them, defining their behavior and
      data.
+ Component
    + A base class for components in an Entity-Component-System (ECS) architecture. Components represent data and
      behavior that can be attached to entities.