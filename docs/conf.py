import sphinx_rtd_theme
import os
import sys

# -- Project information -----------------------------------------------------

project = 'josuFramework'
copyright = '2026, meowcat767. osu! is a trademark of ppy Pty Ltd.'
author = 'meowcat767, ppy Pty Ltd, the osu! community'

version = '0.1.0'
release = '0.1.0'

# -- General configuration ---------------------------------------------------

master_doc = 'index'

extensions = [
    'sphinx_rtd_theme',
]

templates_path = ['_templates']
exclude_patterns = ['_build', 'Thumbs.db', '.DS_Store']

# -- Options for HTML output -------------------------------------------------

html_theme = 'sphinx_rtd_theme'

html_theme_options = {
    'logo_only': False,
    'prev_next_buttons_location': 'bottom',
    'style_external_links': False,
    'vcs_pageview_mode': '',
    # 'style_nav_header_background': 'white',
    'flyout_display': 'hidden',
    'version_selector': True,
    'language_selector': True,

    # Toc options
    'collapse_navigation': True,
    'sticky_navigation': True,
    'navigation_depth': 4,
    'includehidden': True,
    'titles_only': False,
}

# Add any paths that contain custom static files (such as style sheets) here,
# relative to this directory. They are copied after the builtin static files,
# so a file named "default.css" will overwrite the builtin "default.css".
html_static_path = ['_static']

# The name of the Pygments (syntax highlighting) style to use.
pygments_style = 'sphinx'

# The name for this set of Sphinx documents.
# "<project> v<release> documentation" by default.
html_title = 'josuFramework'

# A shorter title for the navigation bar.  Default is the same as html_title.
html_short_title = 'josuFramework'