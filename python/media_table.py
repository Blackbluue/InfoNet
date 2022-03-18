#!/usr/bin/env python3
"""media_table module."""
from sqlalchemy import Column, Integer, String, Date, Text
from sqlalchemy.orm import declarative_base


Base = declarative_base()
table_names = dict()

class Media(Base):
    """Base media table."""

    __tablename__ = "Media"
    id = Column(Integer, primary_key=True)
    title = Column(String(32), nullable=False)
    creator = Column(String(32))
    release_date = Column(Date)
    description = Column(Text)
    type = Column(String(50))

    __mapper_args__ = {
        'polymorphic_identity': 'media',
        'polymorphic_on': type
    }

    def __repr__(self):
        return f"{self.__tablename__}(id={self.id}, title={self.title}, creator={self.creator}, release date={self.release_date}, description={self.description})"
