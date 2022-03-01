#!/usr/bin/env python3
"""media_table module."""
from sqlalchemy import Column, Integer, String, Date, Text
from sqlalchemy.orm import declarative_base


Base = declarative_base()


class Media(Base):
    """Base media table."""

    __tablename__ = "Media"
    id = Column(Integer, primary_key=True)
    title = Column(String(32), nullable=False)
    creator = Column(String(32))
    release_date = Column(Date)
    description = Column(Text)

    def __init__(self, title, creator=None, release_date=None, description=None):
        """Create a record."""
        self.title = title
        self.creator = creator
        self.release_date = release_date
        self.description = description
