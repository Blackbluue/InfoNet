#!/usr/bin/env python3
"""manga_table module."""
from sqlalchemy import Column, Integer, String, Date, Text
from media import Media

class Anime(Media):
    """Manga table."""

    __tablename__ = "Manga"
    chapters = Column(Integer)
    volumes = Column(Integer)
    end_date = Column(Date)

    def __init__(self, chapters=0, volumes=0, end_date=None):
        """Create a record."""
        self.chapters = chapters
        self.volumes = volumes
        self.end_date = end_date
        
