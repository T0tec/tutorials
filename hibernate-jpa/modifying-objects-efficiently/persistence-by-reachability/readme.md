# Transitive Persistence
Real, nontrivial applications work not only with single objects, but rather with networks
of objects. When the application manipulates a network of persistent
objects, the result may be an object graph consisting of persistent, detached, and
transient instances. Transitive persistence is a technique that allows you to propagate
persistence to transient and detached subgraphs automatically.
For example, if you add a newly instantiated Category to the already persistent
hierarchy of categories, it should become automatically persistent without a call to
save() or persist(). We gave a slightly different example in chapter 6, section 6.
4, “Mapping a parent/children relationship,” when you mapped a parent/child
relationship between Bid and Item. In this case, bids were not only automatically
made persistent when they were added to an item, but they were also automatically
deleted when the owning item was deleted. You effectively made Bid an
entity that was completely dependent on another entity, Item (the Bid entity isn’t
a value type, it still supports shared reference).
There is more than one model for transitive persistence. The best known is persistence
by reachability; we discuss it first. Although some basic principles are the
same, Hibernate uses its own, more powerful model, as you’ll see later. The same
