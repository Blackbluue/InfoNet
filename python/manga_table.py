#!/usr/bin/env python3
"""manga_table module."""
from sqlalchemy import Column, Integer, Date, ForeignKey
from python.media_table import Media, table_names

class Manga(Media):
    """Manga table."""

    __tablename__ = "Manga"
    id = Column(Integer, ForeignKey('Media.id'), primary_key=True)
    chapters = Column(Integer)
    volumes = Column(Integer)
    end_date = Column(Date)

    __mapper_args__ = {
        'polymorphic_identity':'manga',
    }

    def __init__(self, chapters=0, volumes=0, end_date=None):
        """Create a record."""
        self.chapters = chapters
        self.volumes = volumes
        self.end_date = end_date
        
table_names[Manga.__tablename__] = Manga  
